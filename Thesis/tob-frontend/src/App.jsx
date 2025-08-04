import "./App.css";
import { Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import { Games, Home, LoginRegister } from "./components/pages/index.js";
import Profile from "./components/pages/Profile.jsx";
import Chat from "./components/pages/Chat.jsx";

function App() {
  const role = localStorage.getItem("role");
  return (
    <div className="App">
      <NavBar />

      <Routes>
        {/*// http://localhost:3000*/}
        <Route path="/" element={<Home />}></Route>

        {/* // http://localhost:3000/games */}
        <Route path="/games" element={<Games />}></Route>
        {/*// http://localhost:3000/chat/valami */}

        <Route
          path="/chat/basic"
          element={<Chat key={Date.now()} mode="basic" />}
        />


        <Route path="/chat/timer-word" element={<Chat mode="timer-word" />} />

        
        <Route
          path="/chat/timer-length"
          element={<Chat mode="timer-length" />}
        />

        {/* // http://localhost:3000/loginRegister */}
        <Route path="/loginRegister" element={<LoginRegister />}></Route>

        {role === "ADMIN" && <Route path="/profile" element={<Profile />} />}
        {role === "USER" && <Route path="/profile" element={<Profile />} />}
      </Routes>
    </div>
  );
}

export default App;
