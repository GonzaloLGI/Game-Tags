import { postRequest } from '../../service/backendService';


export default function Register() {

  async function registerUser(URL, username, password, rewrittenPassword, country, email) {
    if (password == rewrittenPassword) {
      const user = await postRequest(URL, { username: username, password: password, country:country, email:email}, window.localStorage.getItem("token"));
      if (user.username == undefined) {
        console.log("User couldn't register: " + user)
        window.location = "http://localhost:3000/home"
      } else {
        console.log("User registered: " + user.username)
        window.localStorage.setItem("userName", user.username)
        window.location = "http://localhost:3000/home"
      }

    } else {
      console.log("The rewritten password didnt match with the password")
    }
  }

  return (
    <div>
      <form>
        <div>
          <input type="text" id="username" placeholder='Write your username' />
          <input type="password" id="password" placeholder='Write your password' />
          <input type="password" id="rewrittenPassword" placeholder='Rewrite your password' />
          <input type="text" id="country" placeholder='Write your country of origin' />
          <input type="text" id="email" placeholder='Write your email' />
          <input type="button" value="Register" onClick={e => registerUser(
            "/auth/register",
            document.getElementById("username").value,
            document.getElementById("password").value,
            document.getElementById("rewrittenPassword").value,
            document.getElementById("country").value,
            document.getElementById("email").value
          )} />
        </div>
      </form></div>
  )
}