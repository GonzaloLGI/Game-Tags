import Layout from '../../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { putRequest } from '../../service/backendService';
import { getUserRequest, isCompleteInformation, loadModal, unloadModal } from '../../service/miscService';
import { useEffect, useState } from 'react';

export default function ChangePassword() {
  const username = window.localStorage.getItem('userName');
  const [user, setUser] = useState([]);

  useEffect(() => {
    document.title = "GameTags | " + username + " - Change User Password"
    let user = getUserRequest(window.localStorage.getItem("userName"));
    setUser(user)
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function updatePassword(newPassword, oldPassword, rewrittenPassword) {
    if (!isCompleteInformation([newPassword, oldPassword])) {
      loadModal("modalNotInfo")
      return
    }
    if (newPassword == rewrittenPassword) {
      let URL = "/user/password"
      const updatedUser = await putRequest(URL, { newPassword: newPassword, existingPassword: oldPassword }, window.localStorage.getItem("token"));
      if (!updatedUser.username) {
        loadModal("modalIncorrectPassword")
        return
      }
      if (updatedUser.username != undefined) {
        window.localStorage.setItem("userName", updatedUser.username)
        window.localStorage.setItem("token", updatedUser.accessToken)
      }
      setUser(updatedUser)
      loadModal("modalChangePassword")
    }else {
      loadModal("modalNotMatchingPassword")
    }

  }

  return (
    <Layout>
      <div data-title="Change Your Password" data-intro="If, by any reason, you want to change your password, you can do it here">
        <div data-title="Information Needed To Change A Password" data-intro="To change your password you must specify you old password you want to change, the new password you want 
        to change to and reintroduce that same new password, to avoid any misspelling">
          <form>
            <input type="text" id='old' placeholder='Write your old password *' />
            <input type="text" id='new' placeholder='Write your new password *' />
            <input type="password" id="rewritten" placeholder='Rewrite your new password *' />
            <input type='button' value="Submit" onClick={e => updatePassword(document.getElementById("new").value, document.getElementById("old").value,
              document.getElementById("rewritten").value)} />
          </form>
        </div>
      </div>
      <dialog id="modalChangePassword">
        <article>
          <h2>Password changed</h2>
          <footer>
            <input type="button" value="Confirm" onClick={e => {
              unloadModal("modalChangePassword")
              window.location = "https://localhost:3000/profile/userProfile";
            }}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalNotInfo">
        <article>
          <h2>Not enough information</h2>
          <p>
            Please, fill all the fields
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalNotInfo")}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalIncorrectPassword">
        <article>
          <h2>Incorrect password</h2>
          <p>
            Please, enter you correct password
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalIncorrectPassword")}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalNotMatchingPassword">
        <article>
          <h2>The rewritten password doesn't match the new password</h2>
          <p>
              Please, introduce the same password in both fields
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalNotMatchingPassword")}></input>
          </footer>
        </article>
      </dialog>
    </Layout>
  )
}