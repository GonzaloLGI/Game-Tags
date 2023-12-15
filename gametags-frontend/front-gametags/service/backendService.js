import axios from "axios";

const axiosClient = axios.create();

axiosClient.defaults.baseURL = "http://127.0.0.1:8080"
//axiosClient.defaults.baseURL = "http://localhost:8080"
axios.defaults.headers.post['Content-Type'] = 'application/json';

export async function getRequest(URL, token) {
    let response;
    if (token == undefined || token == null) {
        console.log("Sin token " + token)
        response = await axiosClient.get(URL, {
            validateStatus: function (status) {
                return true;
            }
        })
    } else {
        console.log("Con token " + token)
        response = await axiosClient.get(URL, {
            headers: { Authorization: `Bearer ${token}` },
            validateStatus: function (status) {
                return true;
            }
        })
    }

    console.log(response)

    return response.data
}

export async function postRequest(URL, payload, token) {
    let response;
    if (token == undefined || token == null) {
        console.log("Sin token " + token)
        response = await axiosClient.post(URL, payload, {
            validateStatus: function (status) {
                return true;
            }
        })
    } else {
        response = await axiosClient.post(URL, payload, {
            headers: { Authorization: `Bearer ${token}` },
            validateStatus: function (status) {
                return true;
            }
        })
    }
    console.log(response)

    return response.data
}

export async function putRequest(URL, payload, token) {
    let response;
    if (token == undefined || token == null) {
        console.log("Sin token " + token)
        response = await axiosClient.put(URL, payload, {
            validateStatus: function (status) {
                return true;
            }
        })
    } else {
        response = axiosClient.put(URL, payload, {
            headers: { Authorization: `Bearer ${token}` },
            validateStatus: function (status) {
                return true;
            }
        })
    }

    return response.data
}

export async function deleteRequest(URL, token) {
    let response;
    if (token == undefined || token == null) {
        console.log("Sin token " + token)
        response = await axiosClient.delete(URL, {
            validateStatus: function (status) {
                return true;
            }
        })
    } else {
        response = await axiosClient.delete(URL, {
            headers: { Authorization: `Bearer ${token}` },
            validateStatus: function (status) {
                return true;
            }
        })
    }


    return response.data
}