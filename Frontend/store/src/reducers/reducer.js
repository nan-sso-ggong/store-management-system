const initialState = {
	url : "http://13.125.112.60:8080",

	name: "",
	store_name: "",
	access_token: "",
	refresh_token: "",
	store_change: false
}

export default function searchReducer(state = initialState, action) {
	switch (action.type) {
		case 'headquaurters': {
			return {
				...state,
				headName : action.payload.name,
				headCategory : action.payload.category,
				headquaurtersSearch : true
			}
		}
		case 'storeSave': {
			return {
				...state,
				store_name:action.payload.store_name,
				store_change:true
			}
		}
		case 'login': {
			return {
				...state,
				name: action.payload.name,
				access_token: action.payload.access_token,
				refresh_token: action.payload.refresh_token,
			}
		}
		case 'change': {
			return {
				...state,
				store_change:false
			}
		}
		default:
			return state
	}
}