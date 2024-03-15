import Link from "next/link";
import { useEffect } from 'react';


export default function Navbar() {

    useEffect(() => {
        checkUserLogged()
        loadTitle()
    }, [])

    function logOut() {
        window.localStorage.clear()
        window.location = "http://localhost:3000/home"
    }

    function loadTitle() {
        var path = window.location.pathname
        var profile = path.split("/")[1];
        if (profile == "profile") {
            document.getElementById("title-profile").style.display = "block"
            document.getElementById("title").style.display = "none"
        } else {
            document.getElementById("title-profile").style.display = "none"
            document.getElementById("title").style.display = "block"
        }
    }

    function checkUserLogged() {
        let token = window.localStorage.getItem("token")
        if (token == undefined || token == null) {
            document.getElementById("userProfile").style.display = "none"
            document.getElementById("logOut").style.display = "none"
            document.getElementById("uploadVideogame").style.display = "none"
            document.getElementById("user").style.display = "none"
        } else {
            document.getElementById("userProfile").style.display = "block"
            document.getElementById("logOut").style.display = "block"
            document.getElementById("uploadVideogame").style.display = "block"
            document.getElementById("user").style.display = "block"
        }
        document.getElementById("welcome").innerHTML = "Welcome, " + window.localStorage.getItem("userName")
    }

    return <header className="navbarColors">
        <nav className="container-fluid">
            <ul>
                <div id="title-profile">
                    <img src="../images/title_nb.png" alt='' width="300px" height="50px" />
                </div>
                <div id="title">
                    <img src="./images/title_nb.png" alt='' width="300px" height="50px" />
                </div>
            </ul>
            <ul id="user">
                <li id="welcome"></li>
            </ul>
            <ul aria-label="breadcrumb">
                <li>
                    <div>
                        <Link href="/home">Home</Link>
                    </div>
                </li>
                <li>
                    <div id="uploadVideogame">
                        <Link href="/uploadVideogame">Upload Video Game</Link>
                    </div>
                </li>
                <li>
                    <div>
                        <Link href="/search">Search</Link>
                    </div>
                </li>
                <li>
                    <div>
                        <Link href="/profile/login">Log-In</Link>
                    </div>
                </li>
                <li>
                    <div>
                        <Link href="/profile/register">Register</Link>
                    </div>
                </li>
                <li>
                    <div id="userProfile">
                        <Link href="/profile/userProfile">Profile</Link>
                    </div>
                </li>
                <li>
                    <div id="logOut">
                        <input type="button" class="contrast outline" value="Log Out" onClick={e => logOut()} />
                    </div>
                </li>
            </ul>
        </nav>

    </header>
}
