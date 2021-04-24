import { useState } from "react";
import { useHistory } from "react-router";
import Footer from "./Footer";

const Home = () => {

    const [username, setUsername] = useState('');
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(100);
    const history = useHistory();

    const handleRepositories = (e) => {
        e.preventDefault();
        history.push(`/repos/${username}/${page}/${pageSize}`)
    }
    const handleStars = (e) => {
        e.preventDefault();
        history.push(`/stars/${username}`)
    }

    return ( 
        <div className="home">
            <div className="form-div">
            <form>
                <label>Enter a GitHub username:</label>
                <input 
                    type="text"
                    required
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <label>Enter a page number:</label>
                <input 
                    type="number"
                    required
                    min="1"
                    value={page}
                    onChange={e => setPage(e.target.value)}
                />
                <label>Enter number of repositoreis per page:</label>
                <input 
                    type="number"
                    required
                    value={pageSize}
                    min="1"
                    max="100"
                    onChange={e => setPageSize(e.target.value)}
                />
                <button onClick={handleRepositories}> Repositories </button>
                <button onClick={handleStars}> Stars </button>

            </form>
            </div>
            <Footer />
        </div>
     );
}
 
export default Home;