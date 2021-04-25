import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";

const Stars = () => {

    const { username } = useParams();
    const [stars, setStars] = useState(null);
    const [error, setError] = useState(null);


    useEffect(() => {
        const abortController = new AbortController();

        fetch(`http://localhost:8080/stars/${username}`, {
            signal: abortController.signal,
            method: 'GET'
        })
        .then(res => {
            return res.json();
         })
        .then(data => {
            console.log(data.message);
            if (data.message == 
                `User with a username ${username} not found.`
                ) {
                    throw Error(data.message);
                } else if (
                    data.message == "The request per hour limit has been exceeded."
                ) {
                    throw Error(data.message);
                }
                setStars(data);
        })
        .catch(e => {
            setError(e.message);
        });
    }, [])

    return ( 
        <div className="stars">
        { error && (
            <div className="err">
                <p>{ error }</p>
                <Link to="/">
                    Go back to home page.
                </Link>
            </div>
        )}
        { stars && (
            <div className="star">
                <p>{ username }</p>
                <p>⭐{ stars }</p>
            </div>   
        )}
        </div> 
     );
}
 
export default Stars;