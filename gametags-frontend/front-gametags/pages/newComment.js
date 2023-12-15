import Layout from '../components/layout'
import { useEffect, useCallback, useState } from 'react';
import { postRequest } from '../service/backendService';
import { getRequest } from '../service/backendService';
import { useSearchParams } from 'next/navigation';

export default function NewComment() {
  
  const [comment, setComment] = useState([]);
  const [user, setUser] = useState([]);
  console.log("id-videogame: " + useSearchParams().get('id'))
  const videogameName = useSearchParams().get("name")

  useEffect(() => {
    getUser(window.localStorage.getItem("userName"));
  }, [])

  async function addComment(URL, comment) {
    if(window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined){
      window.location="http://localhost:3000/home"
      console.log("Sin credenciales")
      return
    }
    console.log("username: "+window.localStorage.getItem("userName"))
    console.log("user-id: " + user.id)
    const newComment = {text:comment.text,category:comment.category,severity:comment.severity,videogame:comment.videogame,uploadUser:user.username}
    const createdComment = await postRequest(URL,newComment,window.localStorage.getItem("token"));
    setComment(createdComment)
    window.location="http://localhost:3000/search"
  }

  async function getUser(username){
    URL = "/user/name/"+username
    const user = await getRequest(URL,window.localStorage.getItem("token"));
    setUser(user)
  }
  
  return (
    <Layout>
      <div>
        <form>
          <input type='text' id='commentText' placeholder='Write comment text' />

          <label for="category">Select the category of the comment:</label>
          <select name="category" id="category">
            <option value="Sexuality">Sexuality</option>
            <option value="Violence">Violence</option>
            <option value="Drugs">Drugs</option>
            <option value="Profanity">Profanity</option>
            <option value="Misc">Misc</option>
          </select>

          <label for="severity">Select the level of severity for the comment:</label>
          <select name="severity" id="severity">
            <option value="Mild">Mild</option>
            <option value="Moderate">Moderate</option>
            <option value="Severe">Severe</option>
          </select>
          <input type='button' value="Add comment" onClick={e => addComment("/comment/",{text:document.getElementById("commentText").value,
          category:document.getElementById("category").value,severity:document.getElementById("severity").value,videogame:videogameName})}/>
        </form>
      </div>
    </Layout>
  )
}