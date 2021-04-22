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
            if (res.ok) {
                return res.json();
            }
            throw Error('User with that name does not exist.');
         })
        .then(data => {
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
                <p>Username with that name does not exist.</p>
                <Link to="/">
                    Go back to home page.
                </Link>
            </div>
        )}
        { stars && (
            <div className="star">
                <p>GitHub user: { username }</p>
                <p>Stars: { stars }</p>
            </div>   
        )}
        </div> 
     );
}
 
export default Stars;