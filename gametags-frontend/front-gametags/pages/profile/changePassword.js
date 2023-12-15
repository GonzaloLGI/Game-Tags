import Layout from '../../components/layout'
import { getRequest } from '../../service/backendService';
import { putRequest } from '../../service/backendService';
import { useEffect, useCallback, useState } from 'react';

export default function ChangePassword() {

  const [user, setUser] = useState([]);

  useEffect(() => {
    getUser(window.localStorage.getItem("userName"));
  }, [])

  async function updatePassword(newPassword,oldPassword){
    let URL = "/user/password"
    const updatedUser = await putRequest(URL, { newPassword: newPassword, existingPassword: oldPassword },window.localStorage.getItem("token"),window.localStorage.getItem("token"));
    if (updatedUser.username != undefined) {
      window.localStorage.setItem("userName", updatedUser.username)
    }
    setUser(updatedUser)
    window.location="http://localhost:3000/home"
  }

  async function getUser(username){
    console.log(window.localStorage.getItem("userName"));
    let URL = "/user/name/"+username
    const user = await getRequest(URL,window.localStorage.getItem("token"), window.localStorage.getItem("token"));
    setUser(user)
  }

  return (
    <Layout>
      <div>
        <div>
          <form>
            <input type="text" id='old' placeholder='Write your old password' />
            <input type="text" id='new' placeholder='Write your new password' />
            <input type='button' value="Submit" onClick={e => updatePassword(document.getElementById("new").value,document.getElementById("old").value)}/>
          </form>
        </div>
      </div>
    </Layout>
  )
}