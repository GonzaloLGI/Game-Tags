import Link from "next/link";


export default function Navbar() {

    function logOut(){
        window.localStorage.clear()
        window.location="http://localhost:3000/home"
    }

    return <header className="navbar">
        <div className="navbar-start px-3">GAMETAGS navbar</div>


        <div className="navbar-end">
        <div className="ps-3">
                <Link href="/home">Home</Link>
            </div>
            <div className="ps-3">
                <Link href="/uploadVideogame">Upload Videogame</Link>
            </div>
            <div className="ps-3">
                <Link href="/profile/login">Log-In</Link>
            </div>
            <div className="ps-3">
                <Link href="/profile/register">Register</Link>
            </div>
            <div className="ps-3">
                <Link href="/profile/userProfile">Profile</Link>
            </div>
            <div className="ps-3">
                <Link href="/search">Search</Link>
            </div>
            <div>
                <input type="button" value="Log Out" onClick={e => logOut()}/>
            </div>
        </div>

    </header>
}