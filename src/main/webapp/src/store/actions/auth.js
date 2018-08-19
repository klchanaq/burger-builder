import { AUTH_START, AUTH_SUCCESS, AUTH_FAIL } from "./actionTypes";
import axios from "axios";

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

const authSuccess = authData => {
  return {
    type: AUTH_SUCCESS,
    authData: authData
  };
};

const authFail = error => {
  return {
    type: AUTH_FAIL,
    error: error
  };
};

export const auth = (email, password, loginStatus) => {
  return dispatch => {
    dispatch(authStart());

    const fakeUser = fakeUserStore.createNewFakeUser();
    const fakeUserEmail = fakeUser + "@test.com";

    const url =
      loginStatus === "Sign-up"
        ? "/api/authenticate/signup"
        : "/api/authenticate/signin";

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
        } else {
          const err = new Error("Failed to " + loginStatus + " User.");
          console.log(err);
          dispatch(authFail(err));
        }
      },
      1000,
      Math.random()
    );
  };
};
