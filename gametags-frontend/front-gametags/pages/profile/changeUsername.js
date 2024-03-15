import Layout from '../../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { putRequest } from '../../service/backendService';
import { getUserRequest, isCompleteInformation, loadModal, unloadModal } from '../../service/miscService';
import { useEffect, useState } from 'react';

export default function ChangeUsername() {
  const username = window.localStorage.getItem('userName');
  const [user, setUser] = useState([]);

  useEffect(() => {
    document.title = "GameTags | " + username + " - Change User Username"
    let user = getUserRequest(window.localStorage.getItem("userName"));
    setUser(user)
    introJs().setOption("dontShowAgain", true).start();
  }, [])

  async function updateUsername(newUsername, oldUsername, password) {
    if (!isCompleteInformation([newUsername, oldUsername, password])) {
      loadModal("modalNotInfo")
      return
    }
    let URL = "/user/username"
    const updatedUser = await putRequest(URL, { newUsername: newUsername, existingPassword: password }, window.localStorage.getItem("token"));
    if (updatedUser.username != undefined && updatedUser.username != oldUsername) {
      window.localStorage.setItem("userName", updatedUser.username)
      window.localStorage.setItem("token", updatedUser.accessToken)
    } else if (!updatedUser.username) {
      loadModal("modalIncorrectPassword")
      return
    }
    setUser(updatedUser)
    loadModal("modalChangeUsername")
  }

  return (
    <Layout>
      <div data-title="Change You Username" data-intro="If you are bored about your username, you can change it! Don't worry, this new username will be also shown in the comments
      you created with the old username, as well as in the data sheet of the video games you added to the webpage">
        <form data-title="Information For Changing The Username" data-intro="You must specify your old username you want to get rid off, the new username you want to use and your password
        to confirm this change">
          <input type="text" id='old' placeholder='Write your old username *' />
          <input type="text" id='new' placeholder='Write your new username *' />
          <input type="password" id='password' placeholder="Write you password *" />
          <input type='button' value="Submit" onClick={e => updateUsername(document.getElementById("new").value, document.getElementById("old").value,
            document.getElementById("password").value)} />
        </form>
      </div>
      <dialog id="modalChangeUsername">
        <article>
          <h2>Username changed</h2>
          <footer>
            <input type="button" value="Confirm" onClick={e => {
              unloadModal("modalChangeUsername");
              window.location = "http://localhost:3000/profile/userProfile";
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
    </Layout>
  )
}