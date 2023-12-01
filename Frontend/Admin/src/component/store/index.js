import { createStore } from 'redux';
import { rootReducer } from 'app.js';

const store = createStore(rootReducer);

export default store;