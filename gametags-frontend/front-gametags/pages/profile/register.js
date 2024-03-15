import { postRequest } from '../../service/backendService';
import { isCompleteInformation, loadModal, unloadModal } from '../../service/miscService';
import { useEffect } from 'react';


export default function Register() {

  useEffect(() => {
    document.title = "GameTags | Register User"
  }, [])

  async function registerUser(URL, username, password, rewrittenPassword, country, email) {
    if (!isCompleteInformation([username,password,rewrittenPassword,country,email])) {
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
    <div className='authentication' data-theme="dark">
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
      </form>
      <dialog id="modalOk">
        <article>
          <h2>User registered succesfully</h2>
          <p>
              Please, log-in with the new user
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => window.location="http://localhost:3000/home"}></input>
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
    </div>
  )
}