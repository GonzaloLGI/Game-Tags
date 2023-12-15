import Link from 'next/link'
import Layout from '../../components/layout'
import { useEffect, useCallback, useState } from 'react';
import { getRequest } from '../../service/backendService';
import { deleteRequest } from '../../service/backendService';
 
function Profile() {
  const URL = "/user/name/"
  const [user, setUser] = useState([]);

  useEffect(() => {
    if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined){
      window.location="http://localhost:3000/home"
      console.log("Sin credenciales")
      return
    }
    console.log("token: " + window.localStorage.getItem("token"))
    getUser(URL);
  }, [])

  async function getUser(URL) {
    const userName = window.localStorage.getItem("userName")
    console.log(userName)
    URL += userName
    const user = await getRequest(URL,window.localStorage.getItem("token"), window.localStorage.getItem("token"));
    if (user.username != undefined) {
      window.localStorage.setItem("userName", user.username)
    }
    setUser(user)
  }

  function showRoles(user) {
    if (user != undefined && user.roles != undefined) {
      return user.roles.map((element, key) => {
        return (
          <ul>
            <li>{element}</li>
          </ul>
        )
      })
    }
  }

  async function deleteUser(user){
    const deletedUser = await deleteRequest("/user/"+user.id,window.localStorage.getItem("token"),window.localStorage.getItem("token"))
    if(deletedUser != undefined){
      window.location="http://localhost:3000/home"
      console.log("Deleted user: " + deletedUser.username)
    }else{
      console.log("El usuario no se pudo borrar: " + deletedUser)
    }
  }

  return (
    <Layout>
    <ul>
      <li>
        <div>Datos del usuario:
          <ul>
            <li>
              Nombre de usuario: {user.username}
            </li>
            <li>
              Pais de origen: {user.country}
            </li>
            <li>
              Correo electronico: {user.email}
            </li>
            <li>
              Roles: 
              <div>
                  {
                    showRoles(user)
                  }
                </div>
            </li>
          </ul>
        </div>
      </li>
      <li>
        <Link href="/profile/changeUsername">Change user's username</Link>
      </li>
      <li>
        <Link href="/profile/changePassword">Change user's password</Link>
      </li>
      <li>
        <Link href="/profile/searchUserComments">Search for user comments</Link>
      </li>
      <input type='button' value="Delete user" onClick={e => deleteUser(user)}/>
    </ul>
    </Layout>
  )
}
 
export default Profile