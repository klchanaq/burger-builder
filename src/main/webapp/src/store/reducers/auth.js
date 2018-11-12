import { AUTH_START, AUTH_SIGNUP_SUCCESS, AUTH_SUCCESS, AUTH_FAIL, AUTH_LOGOUT } from "../actions/actionTypes";

const initialState = {
  idToken: null,
  localId: null,
  username: null,
  refreshToken: null,
  error: null,
  loading: false
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case AUTH_START:
      return {
        ...state,
        error: null,
        loading: true
      };
    case AUTH_SIGNUP_SUCCESS: 
      return {
        ...state,
        loading: false
      }
    case AUTH_SUCCESS:
      return {
        ...state,
        idToken: action.idToken,
        localId: action.localId,
        username: action.email,
        refreshToken: action.refreshToken,
        error: null,
        loading: false
      };
    case AUTH_FAIL:
      return {
        ...state,
        error: action.error,
        loading: false
      };
    case AUTH_LOGOUT:
      return {
        ...state,
        idToken: null,
        localId: null,
        username: null,
        refreshToken: null
      };
    default:
      return state;
  }
};

export default reducer;