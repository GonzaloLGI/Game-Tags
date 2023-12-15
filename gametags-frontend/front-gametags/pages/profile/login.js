import { postRequest } from '../../service/backendService';


export default function Login() {

  async function loginUser(URL, username, password) {
    const user = await postRequest(URL, { username: username, password: password },window.localStorage.getItem("token"));
    if (user.accessToken == undefined) {
      //CAPTURAR ERROR DEVUELTO POR SERVER
      console.log("User wasn't found: " + user)
      window.location = "http://localhost:3000/home"
    } else {
      console.log("User logged: " + user.username)
      if(window.localStorage.getItem("userName") == undefined || user.username != window.localStorage.getItem("userName")){
        window.localStorage.setItem("userName",user.username)
      }
      window.localStorage.setItem("token",user.accessToken)
      window.location="http://localhost:3000/profile/userProfile"
    }
  }

  return (
    <div>
      <form>
        <div>
          <input type="search" id="username" placeholder='Write your username' />
          <input type="password" id="password" placeholder='Write your password' />
          <input type="button" value="Continue" onClick={e => loginUser(
            "/auth/login",
            document.getElementById("username").value,
            document.getElementById("password").value)} />
        </div>
      </form>
    </div>
  )
}