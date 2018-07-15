import axios from "axios";

/*
    export interface AxiosRequestConfig {
        url?: string;
        method?: string;
        baseURL?: string;
        transformRequest?: AxiosTransformer | AxiosTransformer[];
        transformResponse?: AxiosTransformer | AxiosTransformer[];
        headers?: any;
        params?: any;
        paramsSerializer?: (params: any) => string;
        data?: any;
        timeout?: number;
        withCredentials?: boolean;
        adapter?: AxiosAdapter;
        auth?: AxiosBasicCredentials;
        responseType?: string;
        xsrfCookieName?: string;
        xsrfHeaderName?: string;
        onUploadProgress?: (progressEvent: any) => void;
        onDownloadProgress?: (progressEvent: any) => void;
        maxContentLength?: number;
        validateStatus?: (status: number) => boolean;
        maxRedirects?: number;
        httpAgent?: any;
        httpsAgent?: any;
        proxy?: AxiosProxyConfig | false;
        cancelToken?: CancelToken;
    }
*/

const instance = axios.create({
  baseURL: "http://localhost:8080"
});

export default instance;
