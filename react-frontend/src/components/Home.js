import { useState } from "react";
import { useHistory } from "react-router";

const Home = () => {

    const [username, setUsername] = useState('');
    const history = useHistory();

    const handleRepositories = (e) => {
        e.preventDefault();
        fetch(`http://localhost:8080/repos/${username}`)
        .then(() => {
            history.push(`/repos/${username}`)
        });
    }
    const handleStars = (e) => {
        e.preventDefault();
        fetch(`http://localhost:8080/stars/${username}`)
        .then(() => {
            history.push(`/stars/${username}`)
        });
    }

    return ( 
        <div className="home">
            <form>
                <label>Enter a GitHub username: </label>
                <input 
                    type="text"
                    required
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <button onClick={handleRepositories}> Repositories </button>
                <button onClick={handleStars}> Stars </button>

            </form>
        </div>
     );
}
 
export default Home;