import {
  AUTH_START,
  AUTH_SIGNUP_SUCCESS,
  AUTH_SUCCESS,
  AUTH_FAIL,
  AUTH_LOGOUT
} from "./actionTypes";
import axios from "../../axios-orders";

/* JavaScript Closure Function */
function generateFakeUser() {
  var fakeUsername = "fakeUser";
  var fakeUserCounter = 0;

  function incrementFakeUserCounter() {
    fakeUserCounter++;
  }

  function decrementFakeUserCounter() {
    fakeUserCounter--;
  }

  function getCurrentFakeUser() {
    return fakeUsername + fakeUserCounter;
  }

  function createNewFakeUser() {
    return fakeUsername + ++fakeUserCounter;
  }

  return {
    incrementFakeUserCounter,
    decrementFakeUserCounter,
    getCurrentFakeUser,
    createNewFakeUser
  };
}

const fakeUserStore = generateFakeUser();

const authStart = () => {
  return {
    type: AUTH_START
  };
};

const authSignUpSuccess = () => {
  return {
    type: AUTH_SIGNUP_SUCCESS
  };
};

const authSuccess = jwttoken => {
  return {
    type: AUTH_SUCCESS,
    authData: jwttoken
  };
};

const authFail = error => {
  return {
    type: AUTH_FAIL,
    error: error
  };
};

const authLogout = () => {
  return {
    type: AUTH_LOGOUT
  };
};

const checkAuthTimeout = expirationTime => {
  // return a dispatch function inside another dispatch function (function: auth), why ?
  // We want to do some async tasks here using redux-thunk, and the returned result will be handled by dispatch([action]).
  // in normal cases, redux-thunk's dispatch([action]) handles an action (in the format of { type, payload }) and dispatch function (in the format of (dispatch) => {}).
  return dispatch => {
    setTimeout(() => {
      dispatch(authLogout());
    }, Number(expirationTime) * 1000);
  };
};

export const auth = (email, password, loginStatus) => {
  return dispatch => {
    dispatch(authStart());

    const url =
      loginStatus === "Sign-up" ? "/api/register" : "/api/authenticate";

    const loginData = {
      email: email,
      password: password
    };

    axios
      .post(url, loginData)
      .then(response => {
        const jwttoken = response.data;
        localStorage.setItem("idToken", jwttoken.idToken);
        localStorage.setItem("localId", jwttoken.localId);
        localStorage.setItem("username", jwttoken.email);
        console.log("jwttoken: ", jwttoken);
        dispatch(authSuccess(jwttoken));
      })
      .catch(error => {
        console.log(error);
        const err = new Error("Failed to " + loginStatus + " User.");
        if (loginStatus === "Sign-up") {
        } else {
          dispatch(authFail(err));
        }
      });

    /*

    const fakeUser = fakeUserStore.createNewFakeUser();
    const fakeUserEmail = fakeUser + "@test.com";

    const loginData = {
      username: null,
      email: fakeUserEmail,
      password: password
    };

    setTimeout(
      randomNum => {
        if (randomNum > 0.2) {
          const response = {
            data: {
              email: fakeUserEmail,
              expiresIn: "3600",
              idToken: fakeUser + "_idToken",
              kind: "identityToolKit#SignupNewUserResponse",
              localId: fakeUser + "_localId",
              refreshToken: fakeUser + "_refreshToken",
              message: loginStatus + " successfully"
            }
          };
          const fakedAuthData = response.data;
          console.log("fakedAuthData: ", fakedAuthData);
          dispatch(authSuccess(fakedAuthData));
          dispatch(checkAuthTimeout(fakedAuthData.expiresIn)); // another async task here.
        } else {
          const err = new Error("Failed to " + loginStatus + " User.");
          console.log(err);
          dispatch(authFail(err));
        }
      },
      1000,
      Math.random()
    );

    */
  };
};

export const logout = () => {
  return dispatch => {
    localStorage.removeItem("idToken");
    localStorage.removeItem("localId");
    localStorage.removeItem("username");
    dispatch(authLogout());
  };
};
