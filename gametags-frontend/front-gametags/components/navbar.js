import Link from "next/link";
import { useEffect, useState } from 'react';
import { getRequest } from '../service/backendService';
import { loadImage } from "../service/miscService"


export default function Navbar() {

    const [user, setUser] = useState([]);

    useEffect(() => {
        getUser()
        checkUserLogged()
        loadTitle()
    }, [])

    function logOut() {
        window.localStorage.clear()
        window.location = "https://localhost:3000/home"
        activateProfileImage = false
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
            document.getElementById("userProfileResponsive").style.display = "none"
            document.getElementById("logOut").style.display = "none"
            document.getElementById("logOutResponsive").style.display = "none"
            document.getElementById("uploadVideogame").style.display = "none"
            document.getElementById("uploadVideogameResponsive").style.display = "none"
        } else {
            document.getElementById("userProfile").style.display = "block"
            document.getElementById("userProfileResponsive").style.display = "block"
            document.getElementById("logOut").style.display = "block"
            document.getElementById("logOutResponsive").style.display = "block"
            document.getElementById("uploadVideogame").style.display = "block"
            document.getElementById("uploadVideogameResponsive").style.display = "block"
        }
    }

    async function getUser() {
        const userName = window.localStorage.getItem("userName")
        if (!userName) {
            return
        }
        let URL = "/user/name/" + userName
        const user = await getRequest(URL, window.localStorage.getItem("token"));
        if (user.username != undefined) {
            window.localStorage.setItem("userName", user.username)
        }
        setUser(user)
    }

    function enableResponsiveOptions() {
        let token = window.localStorage.getItem("token")
        var element = token == undefined || token == null ? document.getElementById("responsiveOptionsNotLogged") : document.getElementById("responsiveOptionsLogged");
        if (element.style.display == "none") {
            element.style.display = " block";
        } else {
            element.style.display = "none";
        }
    }

    return <header className="navbarColors">
        <nav class="menuBig">
            <ul class="titleLogo">
                <div id="title-profile">
                    <img src="../images/title_nb.png" alt='' />
                </div>
                <div id="title">
                    <img src="./images/title_nb.png" alt='' />
                </div>
            </ul>
            <ul class="navBarElements" style={{ "float": "right" }}>
                <li class="userIcon">
                    <div>
                        {loadImage(user, "navbar")}
                    </div>
                </li>
                <li>
                    <h4>
                        <Link href="/home">Home</Link>
                    </h4>
                </li>
                <li>
                    <h4 id="uploadVideogame">
                        <Link href="/uploadVideogame">Upload Video Game</Link>
                    </h4>
                </li>
                <li>
                    <h4>
                        <Link href="/search">Search</Link>
                    </h4>
                </li>
                <li>
                    <h4>
                        <Link href="/profile/login">Log-In</Link>
                    </h4>
                </li>
                <li>
                    <h4>
                        <Link href="/profile/register">Register</Link>
                    </h4>
                </li>
                <li>
                    <h4 id="userProfile">
                        <Link href="/profile/userProfile">Profile</Link>
                    </h4>
                </li>
                <li>
                    <h4 id="logOut">
                        <input type="button" class="contrast outline" value="Log Out" onClick={e => logOut()} />
                    </h4>
                </li>
            </ul>
        </nav>
        <div class="menuResponsive">
            <nav>
                <ul class="titleLogo">
                    <li>
                        <div id="title-profile">
                            <img src="../images/title_nb.png" alt='' />
                        </div>
                    </li>
                    <li class="userIcon">
                        {loadImage(user, "navbar")}
                    </li>
                </ul>
                <ul>

                    <li>
                        <img src="../images/menu.png" class="menuIcon" style={{ float: "right" }} onClick={e => enableResponsiveOptions()}></img>
                    </li>
                </ul>
            </nav>
            <aside>
                <nav>
                    <article id="responsiveOptionsNotLogged" style={{ display: "none" }}>
                        <ul class="dropdownResponsive">
                            <li><a href="/home">Home</a></li>
                            <li><a href="/search">Search</a></li>
                            <li><a href="/profile/login">Log-In</a></li>
                            <li><a href="/profile/register">Register</a></li>
                        </ul>
                    </article>
                    <article id="responsiveOptionsLogged" style={{ display: "none" }}>
                        <ul class="dropdownResponsive">
                            <li><a href="/home">Home</a></li>
                            <li><a id="uploadVideogameResponsive" href="/uploadVideogame">Upload Video Game</a></li>
                            <li><a href="/search">Search</a></li>
                            <li><a href="/profile/login">Log-In</a></li>
                            <li><a href="/profile/register">Register</a></li>
                            <li><a id="userProfileResponsive" href="/profile/userProfile">Profile</a></li>
                            <li><a id="logOutResponsive" onClick={e => logOut()}>Log-Out</a></li>
                        </ul>
                    </article>
                </nav>
            </aside>
        </div>
    </header>
}
