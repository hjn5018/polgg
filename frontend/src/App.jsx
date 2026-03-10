import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Login from './pages/Login';
import Signup from './pages/Signup';
import Home from './pages/Home';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app-wrapper">
        <nav className="navbar glass">
          <div className="container nav-content">
            <Link to="/" className="logo gradient-text">POL.GG</Link>
            <div className="nav-links">
              <Link to="/login" className="nav-btn secondary">Login</Link>
              <Link to="/signup" className="nav-btn primary">Sign Up</Link>
            </div>
          </div>
        </nav>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
