import React, { Component } from "react";
import Aux from "../HOC";
import Modal from "../../components/UI/Modal/Modal";

const withErrorHandler = (WrappedComponent, axios) => {
  return class extends Component {
    state = {
      error: null
    };

    componentWillMount() {
      // Add a request interceptor
      this.requestInterceptor = axios.interceptors.request.use(
        config => {
          // Do something before request is sent
          if (this.state.error) {
            this.setState({ error: null });
          }
          return config;
        },
        error => {
          // Do something with request error
          this.setState({ error: error });
          return Promise.reject(error);
        }
      );

      // Add a response interceptor
      this.responseInterceptor = axios.interceptors.response.use(
        response => {
          // Do something with response data
          return response;
        },
        error => {
          // Do something with response error
          console.log("[withErrorHandler] ", error);
          this.setState({ error: error });
          return Promise.reject(error);
        }
      );
    }

    componentWillUnmount() {
      console.log(
        "[withErrorHandler] Will unmount",
        this.requestInterceptor,
        this.responseInterceptor
      );
      axios.interceptors.request.eject(this.requestInterceptor);
      axios.interceptors.response.eject(this.responseInterceptor);
    }

    errorConfirmedHandler = $event => {
      this.setState({ error: null });
    };

    render() {
      return (
        <Aux>
          <Modal
            show={this.state.error}
            modalClosed={this.errorConfirmedHandler}
          >
            {this.state.error ? this.state.error.message : null}
          </Modal>
          <WrappedComponent {...this.props} />
        </Aux>
      );
    }
  };
};

export default withErrorHandler;
