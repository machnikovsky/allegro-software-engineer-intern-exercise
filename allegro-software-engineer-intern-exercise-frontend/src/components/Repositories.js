import { useState, useEffect } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";

const Repositories = () => {
    const { username } = useParams();
    const [repositories, setRepositories] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const abortController = new AbortController();

        fetch(`http://localhost:8080/repos/${username}`, {
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
            setRepositories(data);
        })
        .catch(e => {
            setError(e.message);
        });
    }, [])

    return (
        <div className="repositories">
        { error && (
            <div className="err">
                <p>Username with that name does not exist.</p>
                <Link to="/">
                    Go back to home page.
                </Link>
            </div>
        )}
        { repositories && (
            
                repositories.map(repo => (
                    <div className="repository" key= { repo.name }>
                        <p>Repository name: { repo.name }</p>
                        <p>Stars: { repo.stars }</p>
                    </div>
                ))
            
        )}
        </div> 
     );
}
 
export default Repositories;