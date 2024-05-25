import Link from 'next/link'
import Layout from '../../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { deleteRequest, postRequest, getRequest } from '../../service/backendService';
import { loadModal, unloadModal, loadImage } from '../../service/miscService';

export default function Profile() {
  const [user, setUser] = useState([]);

  useEffect(() => {
    if (window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined) {
      window.location = "https://localhost:3000/home"
      return
    }
    getUser();
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function getUser() {
    const userName = window.localStorage.getItem("userName")
    let URL = "/user/name/" + userName
    const user = await getRequest(URL, window.localStorage.getItem("token"));
    if (user.username != undefined) {
      window.localStorage.setItem("userName", user.username)
      if(user.profileImageData != undefined || user.profileImageData != null){
        window.localStorage.setItem("userImage", user.profileImageData.data)
      }
      document.title = "GameTags | Profile"
    }
    setUser(user)
  }

  function showRoles(user) {
    let role = ""
    if (user != undefined && user.roles != undefined) {
      return user.roles.map((element, key) => {
        if (element == "ROLE_USER") {
          role = "User"
        } else if (element == "ROLE_ADMIN") {
          role = "Admin"
        }
        return (
          <ul className='userData'>
            <li>{role}</li>
          </ul>
        )
      })
    }
  }

  async function deleteUser(user) {
    const deletedUser = await deleteRequest("/user/" + user.id, window.localStorage.getItem("token"))
    if (deletedUser != undefined) {
      loadModal("modalUserDeleted")
    } else {
      loadModal("modalUserNotDeleted")
    }
  }

  async function uploadProfileImage(URL, image) {
    if (image == null || image == undefined) {
      loadModal("modalNotImage")
      return
    }
    await postRequest(URL + user.id + "/image", image, window.localStorage.getItem("token"))
    window.location = "https://localhost:3000/profile/userProfile"
  }

  return (
    <Layout>
      <article data-title="User Profile" data-intro="When you have succesfully registered a user and logged in, the information about you is shown here. From here you have multiple options
      like changing your username or your password, uploading a profile picture or searching your comments">
        <div className='profileImageDisplay'>
          {loadImage(user, "profile")}
        </div>
        <article>
          <h1>
            {user.username}
          </h1>
          <div className='userData'>
            Country: {user.country}
          </div>
          <div className='userData'>
            Email: {user.email}
          </div>
          <div className='userData'>
            Roles:
            <div>
              {
                showRoles(user)
              }
            </div>
          </div>
        </article>
      </article>
      <div>
        <article>
          <div>Upload user profile picture</div>
          <div>
            <input type="file" id="profileImage" name="image" accept="image/*" />
            <input type="button" value="Submit profile picture" onClick={e => uploadProfileImage("/user/", document.getElementById("profileImage").files[0])} />
          </div>
        </article>
        <article>
          <div className='userOptions'>
            <Link href="/profile/changeUsername">
              <input type='button' value="Change user's username"/>
            </Link>
          </div>
          <div className='userOptions'>
            <Link href="/profile/changePassword">
              <input type='button' value="Change user's password"/>
            </Link>
          </div>
          <div className='userOptions'>
            <Link href="/profile/searchUserComments">
              <input type='button' value="Search for user comments"/>
            </Link>
          </div>
          <div>
            <input type='button' value="Delete user" onClick={e => deleteUser(user)} />
          </div>
        </article>
      </div>
      <dialog id="modalNotImage">
        <article>
          <h2>There wasn't submitted any image</h2>
          <p>
            Please, submit a valid image
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalNotImage")}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalUserDeleted">
        <article>
          <h2>User completely deleted</h2>
          <p>
            You will be redirected to Home page
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => {
              unloadModal("modalUserDeleted");
              window.localStorage.clear();
              window.location = "https://localhost:3000/home";
            }}></input>
          </footer>
        </article>
      </dialog>
      <dialog id="modalUserNotDeleted">
        <article>
          <h2>User couldn't be deleted</h2>
          <p>
            Please, try again
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => {
              unloadModal("modalUserNotDeleted");
            }}></input>
          </footer>
        </article>
      </dialog>
    </Layout>
  )
}