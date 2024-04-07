import { postRequest } from '../../service/backendService';
import { loadModal, unloadModal } from '../../service/miscService';
import { useEffect } from 'react';
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import LayoutAuthentication from '../../components/layout_authentication';


export default function Login() {

  useEffect(() => {
    document.title = "GameTags | Log-In"
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
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
      window.location = "https://localhost:3000/profile/userProfile"
    }
  }

  return (
    <LayoutAuthentication>
      <div className='authentication' data-theme="dark" data-title='Log-In With An Existing User' data-intro="Once you have registered an 
    user, you can log-in. You have to write the user's name and the password associated with the user">
        <form>
          <div>
            <input class="loginFont" type="text" id="username" placeholder='Write your username' />
            <input class="loginFont" type="password" id="password" placeholder='Write your password' />
            <input type="button" value="Continue" onClick={e => loginUser(
              "/auth/login",
              document.getElementById("username").value,
              document.getElementById("password").value)} />
          </div>
        </form>
      </div>
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
    </LayoutAuthentication>

  )
}