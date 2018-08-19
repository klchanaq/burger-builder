import { AUTH_START, AUTH_SUCCESS, AUTH_FAIL } from "../actions/actionTypes";

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
    default:
      return state;
  }
};

export default reducer;
