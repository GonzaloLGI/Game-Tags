import Layout from '../../components/layout'
import { getRequest } from '../../service/backendService';
import { putRequest } from '../../service/backendService';
import { useEffect, useCallback, useState } from 'react';

export default function ChangeUsername() {
  
  const [user, setUser] = useState([]);

  useEffect(() => {
    getUser(window.localStorage.getItem("userName"));
  }, [])

  async function updateUsername(newUsername,oldUsername,password){
    let URL = "/user/username"
    const updatedUser = await putRequest(URL, { newUsername: newUsername, password: password },window.localStorage.getItem("token"),window.localStorage.getItem("token"));
    if (updatedUser.username != undefined && updatedUser.username != oldUsername) {
      window.localStorage.setItem("userName", updatedUser.username)
    }
    setUser(updatedUser)
    window.location="http://localhost:3000/home"
  }

  async function getUser(username){
    console.log(window.localStorage.getItem("userName"),window.localStorage.getItem("token"));
    let URL = "/user/name/"+username
    const user = await getRequest(URL, window.localStorage.getItem("token"));
    setUser(user)
  }

  return (
    <Layout>
      <div>
        <form>
        <input type="text" id='old' placeholder='Write your old username' />
        <input type="text" id='new' placeholder='Write your new username' />
        <input type="password" id='password' placeholder="Write you password"/>
        <input type='button' value="Submit" onClick={e => updateUsername(document.getElementById("new").value,document.getElementById("old").value,
          document.getElementById("password").value)}/>
        </form>
      </div>
    </Layout>
  )
}