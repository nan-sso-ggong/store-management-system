import React, {useState, useEffect} from "react";
import axios from "axios";

export function Api(props){

    const [item, setitem] = useState([])

    function content(props){

        const link = "http://34.64.95.170:8080/" + props
        axios.get(link)
        .then(function(responseHandler) {
            setitem((item) => responseHandler.data);
        })
    }

    useEffect(() => {
        content(props)
    }, [])

    return item
}

export default Api