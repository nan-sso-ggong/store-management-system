import { combineReducers } from 'redux'

import searchReducer from './component/reducers/searchManage'

const rootReducer = combineReducers({
	search: searchReducer,
})

export default rootReducer