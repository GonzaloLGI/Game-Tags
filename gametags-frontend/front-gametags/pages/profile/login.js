import { postRequest } from '../../service/backendService';
import { loadModal, unloadModal } from '../../service/miscService';
import { useEffect } from 'react';


export default function Login() {

  useEffect(() => {
    document.title = "GameTags | Log-In"
  }, [])

  async function loginUser(URL, username, password) {
    const user = await postRequest(URL, { username: username, password: password }, window.localStorage.getItem("token"));
    if (user.accessToken == undefined) {
      loadModal("modalKo")
    } else {
      if (window.localStorage.getItem("userName") == undefined || user.username != window.localStorage.getItem("userName")) {
        window.localStorage.setItem("userName", user.username)
      }
      window.localStorage.setItem("token", user.accessToken)
      window.location = "http://localhost:3000/profile/userProfile"
    }
  }

  return (
    <div className='authentication' data-theme="dark">
      <form>
        <div>
          <input type="text" id="username" placeholder='Write your username' />
          <input type="password" id="password" placeholder='Write your password' />
          <input type="button" value="Continue" onClick={e => loginUser(
            "/auth/login",
            document.getElementById("username").value,
            document.getElementById("password").value)} />
        </div>
      </form>
      <dialog id="modalKo">
        <article>
          <h2>User couldn't be found</h2>
          <p>
            Please, try again
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => { unloadModal("modalKo") }}></input>
          </footer>
        </article>
      </dialog>
    </div>
  )
}