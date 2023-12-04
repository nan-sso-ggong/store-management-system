package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Order
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.domain.*
import edu.dongguk.cs25server.domain.type.OrderStatus
import edu.dongguk.cs25server.dto.request.CustomerOrderRequestDto
import edu.dongguk.cs25server.dto.request.CustomerPickupRequestDto
import edu.dongguk.cs25server.dto.response.CustomerOrderResponseDto
import edu.dongguk.cs25server.dto.response.ListResponseDto
import edu.dongguk.cs25server.dto.response.PageInfo
import edu.dongguk.cs25server.domain.type.PaymentType
import edu.dongguk.cs25server.dto.request.*
import edu.dongguk.cs25server.dto.response.*
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.StoreRepository
import edu.dongguk.cs25server.util.Log.Companion.log
import edu.dongguk.cs25server.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val storeRepository: StoreRepository,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val itemCSRepository: ItemCSRepository,
    private val pointRepository: PointRepository
) {

    // 점주 - 주문 내역 조회
    fun readCustomerOrder(storeId: Long, customerOrderRequestDto: CustomerOrderRequestDto): ListResponseDto<List<CustomerOrderResponseDto>> {
        log.info("order_state: {}", customerOrderRequestDto.order_state)
        log.info("start_date: {}", customerOrderRequestDto.start_date)
        val paging: Pageable = PageRequest.of(
            customerOrderRequestDto.page.toInt(),
            customerOrderRequestDto.size
        )
        val store: Store = storeRepository.findByIdOrNull(storeId) ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)
        val customerOrderList: Page<OrderRepository.CustomerOrderInfo> =
            orderRepository.findCustomerOrderByStore(store, OrderStatus.getOrderStatus(customerOrderRequestDto.order_state)
                , customerOrderRequestDto.start_date, customerOrderRequestDto.end_date, paging)

        val pageInfo: PageInfo = PageInfo(
            page = customerOrderRequestDto.page.toInt(),
            size = customerOrderRequestDto.size,
            totalElements = customerOrderList.totalElements.toInt(),
            totalPages = customerOrderList.totalPages
        )

        val customerOrderDtolist: List<CustomerOrderResponseDto> = customerOrderList
            .map { o ->
                CustomerOrderResponseDto(
                    o.getCNAME(),
                    o.getOID(),
                    o.getINAME(),
                    o.getPRICE(),
                    o.getCREATE(),
                    o.getSTATUS().toString()
                )
            }
            .toList()

        return ListResponseDto(customerOrderDtolist, pageInfo)
    }

    // 점주 - 픽업 완료 처리
    fun pickupCustomerOrder(storeId: Long, customerPickupDtos: List<CustomerPickupRequestDto>): Boolean {
        customerPickupDtos.map { dto ->
            orderRepository.findByIdOrNull(dto.customer_order_id)
                ?.updateOrderStatus(OrderStatus.PICKUP)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_ORDER)
        }
        return true
    }

    // 소비자 결제
    fun payment(storeId: Long, customerId: Long, paymentRequest: CustomerPaymentRequest): PaymentResponse {
        val paymentType: PaymentType = PaymentType.getPaymentType(paymentRequest.type)
            ?: throw GlobalException(ErrorCode.WRONG_PAYMENT_TYPE_ERROR)

        val customer = customerRepository.findByIdOrNull(customerId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER)

        customer.checkBalance(paymentRequest.totalPrice, paymentRequest.point)

        val store: Store = storeRepository.findByIdOrNull(storeId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_STORE)

        val itemIds: List<Long> = paymentRequest.items.map { it.id }
        val itemCSMap = itemCSRepository.findByIdsAndStore(storeId, itemIds).associateBy { it.getId() }

        val orderItems: List<OrderItemCS> = paymentRequest.items.map { item: ItemCSPaymentDto ->
            val itemCS = itemCSMap[item.id]
                ?: throw GlobalException(ErrorCode.NOT_FOUND_ITEMCS)
            OrderItemCS.createOrderItem(itemCS, itemCS.price, item.quantity)
        }

        orderItems.forEach { orderItem -> orderItem.itemCS.checkStock(orderItem.quantity) }
        orderItems.forEach { orderItem -> orderItem.itemCS.removeStock(orderItem.quantity) }

        val order = Order.createOrder(customer, store,
            paymentRequest.point, paymentRequest.totalPrice,
            paymentRequest.itemName, paymentRequest.itemCount,
            paymentType, orderItems)

        val savedPoint = customer.payment(paymentRequest.totalPrice, paymentRequest.point)
        val point = Point(balance = customer.getPointBalance(), amount = savedPoint)

        point.setCustomer(customer)
        order.setPoint(point)

        pointRepository.save(point)
        orderRepository.save(order)

        val totalPoint: Int = pointRepository.findTotalPointByCustomer(customerId)
        customer.updateMembership(totalPoint)

        return order.toHistoryResponse()
    }

    // 소비자 주문 내역 조회
    fun readHistory(customerId: Long, readHistory: ReadHistoryRequest): ListResponseDto<List<PaymentResponse>> {
        val customer = customerRepository.findByIdOrNull(customerId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_CUSTOMER)

        val paging: Pageable =  PageRequest.of(
            readHistory.page,
            readHistory.size,
            Sort.by(Sort.Direction.DESC, "createdAt")
        )
        val orderStatus = OrderStatus.getOrderStatus(readHistory.status)

        val orders : Page<Order> = if (orderStatus == null) {
            orderRepository.findByCustomer(customer, paging)
        } else {
            orderRepository.findByCustomerAndOrderStatus(customer, orderStatus, paging)
        }

        val histories = orders.content.map(Order::toHistoryResponse)
        return ListResponseDto(datalist = histories, pageInfo =  PageInfo(
            page = readHistory.page,
            size = readHistory.size,
            totalElements = orders.totalElements.toInt(),
            totalPages = orders.totalPages))
    }

    fun readHistoryDetail(orderId: Long): PaymentResponse {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_ORDER)

        return order.toHistoryResponse()
    }

    fun refund(orderId: Long, customerId: Long): Boolean {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw GlobalException(ErrorCode.NOT_FOUND_ORDER)

        order.refund()
        return true
    }


}