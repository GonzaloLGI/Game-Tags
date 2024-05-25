import { postRequest } from '../../service/backendService';
import { isCompleteInformation, loadModal, unloadModal } from '../../service/miscService';
import { useEffect } from 'react';
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import LayoutAuthentication from '../../components/layout_authentication';


export default function Register() {

  useEffect(() => {
    document.title = "GameTags | Register User"
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function registerUser(URL, username, password, rewrittenPassword, country, email) {
    if (!isCompleteInformation([username, password, rewrittenPassword, country, email])) {
      loadModal("modalKo")
      return
    }
    if (password == rewrittenPassword) {
      const user = await postRequest(URL, { username: username, password: password, country: country, email: email }, window.localStorage.getItem("token"));
      if (user.username == undefined) {
        loadModal("modalKo")
      } else {
        window.localStorage.setItem("userName", user.username)
        loadModal("modalOk")
      }
    } else {
      loadModal("modalIncorrectPassword")
    }
  }

  return (
    <LayoutAuthentication>
      <div className='authentication' data-theme="dark" data-title='Registering A New User' data-intro="If you want to do things such as adding
    new video games, write comments or add new tags to existing games; you must first have an user.">
        <form>
          <div data-title='Required Information' data-intro="If you haven't yet, here you can register a new user. 
        To do this you have to give some information, like the name you would like to have or an email for contact">
            <input class="registerFont" type="text" id="username" placeholder='Write your username *' />
            <input class="registerFont" type="password" id="password" placeholder='Write your password *' />
            <input class="registerFont" type="password" id="rewrittenPassword" placeholder='Rewrite your password *' />
            <input class="registerFont" type="text" id="country" placeholder='Write your country of origin *' />
            <input class="registerFont" type="text" id="email" placeholder='Write your email *' />
            <input class="registerFont" type="button" value="Register" onClick={e => registerUser(
              "/auth/register",
              document.getElementById("username").value,
              document.getElementById("password").value,
              document.getElementById("rewrittenPassword").value,
              document.getElementById("country").value,
              document.getElementById("email").value
            )} />
          </div>
        </form>
      </div>
      <dialog id="modalOk">
          <article>
            <h2>User registered succesfully</h2>
            <p>
              Please, log-in with the new user
            </p>
            <footer>
              <input type="button" value="Confirm" onClick={e => window.location = "https://localhost:3000/home"}></input>
            </footer>
          </article>
        </dialog>
      <dialog id="modalKo">
        <article>
          <h2>User couldn't be registered</h2>
          <p>
            Please, try again
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalKo")}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalIncorrectPassword">
        <article>
          <h2>The rewritten password doesn't match the first password</h2>
          <p>
            Please, introduce the same password in both fields
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalIncorrectPassword")}></input>
          </footer>
        </article>
      </dialog>
    </LayoutAuthentication>

  )
}