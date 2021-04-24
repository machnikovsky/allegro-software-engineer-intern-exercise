import Home from './components/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'; 
import Repositories from './components/Repositories';
import Navbar from './components/Navbar';
import Stars from './components/Stars';
import Footer from './components/Footer';


function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Switch>
          <Route exact path="/">
            <Home />  
          </Route>
          <Route exact path="/repos/:username/:page/:pageSize">
            <Repositories />  
          </Route>
          <Route exact path="/stars/:username">
            <Stars />  
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
