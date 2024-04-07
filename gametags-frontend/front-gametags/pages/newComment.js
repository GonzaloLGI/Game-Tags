import Layout from '../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { postRequest } from '../service/backendService';
import { useSearchParams } from 'next/navigation';
import { getUserRequest, isCompleteInformation, loadModal, unloadModal } from '../service/miscService';

export default function NewComment() {

  const [comment, setComment] = useState([]);
  const [user, setUser] = useState([]);
  const videogameName = useSearchParams().get("name")

  useEffect(() => {
    document.title = "GameTags | " + videogameName + " - Create Comment"
    let user = getUserRequest(window.localStorage.getItem("userName"));
    setUser(user)
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function addComment(URL, comment) {
    if (!isCompleteInformation([comment.text, comment.category, comment.severity])) {
      loadModal("modalNotInfo")
      return
    }
    if (window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined) {
      window.location = "https://localhost:3000/home"
      return
    }
    const newComment = { text: comment.text, category: comment.category, severity: comment.severity, videogame: comment.videogame, uploadUser: window.localStorage.getItem("userName") }
    const createdComment = await postRequest(URL, newComment, window.localStorage.getItem("token"));
    setComment(createdComment)
    window.localStorage.setItem("name", videogameName)
    window.location = "https://localhost:3000/selectedVideogame?name=" + encodeURIComponent(videogameName)
  }

  return (
    <Layout>
      <div data-title="Creating A New Comment" data-intro="Here is where you can share your opinion about the contents of the video game. You must select the theme/category of the 
      comment, the level of severity of the theme you consider the video game has and your justification about your opinion and why is that severe">
        <form>
          <input type='text' id='commentText' placeholder='Write comment text *' />

          <label for="category">Select the category of the comment *:</label>
          <select name="category" id="category">
            <option value="Sexuality">Sexuality</option>
            <option value="Violence">Violence</option>
            <option value="Drugs">Drugs</option>
            <option value="Language">Language</option>
            <option value="Misc">Misc</option>
          </select>

          <label for="severity">Select the level of severity for the comment *:</label>
          <select name="severity" id="severity">
            <option value="Mild">Mild</option>
            <option value="Moderate">Moderate</option>
            <option value="Severe">Severe</option>
          </select>
          <input type='button' value="Add comment" onClick={e => addComment("/comment/", {
            text: document.getElementById("commentText").value,
            category: document.getElementById("category").value, severity: document.getElementById("severity").value, videogame: videogameName
          })} />
        </form>
      </div>
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
    </Layout>
  )
}