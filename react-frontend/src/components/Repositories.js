import { useState, useEffect } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import Footer from "./Footer";

const Repositories = () => {
    const { username, page, pageSize } = useParams();
    const [repositories, setRepositories] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        console.log(`/repos/${username}?page=${page}&page_size=${pageSize}`)
        const abortController = new AbortController();

        fetch(`http://localhost:8080/repos/${username}?page=${page}&page_size=${pageSize}`, {
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
            <div className="header">
                { username } repositories:
                <hr/>
            </div>
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
                            <p>{ repo.name }</p>
                            <p>⭐{ repo.stars }</p>
                        </div>
                    ))
                
            )}
        <Footer />
        </div> 
     );
}
 
export default Repositories;