import Layout from '../../components/layout'
import { postRequest } from '../../service/backendService';
import { loadModal, unloadModal, showRating, fixDate } from '../../service/miscService';
import { useEffect, useState } from 'react';
import Link from 'next/link'

export default function SearchUserComments() {
  const username = window.localStorage.getItem('userName');
  const [comments, setComments] = useState([]);

  useEffect(() => {
    document.title= "GameTags | " + username + " - Search User Comments"
  }, [])

  async function searchBy(theme,filter){
    let comments;
    if(theme != "videogame"){
      comments = await postRequest("/comment/"+theme+"/user", filter, window.localStorage.getItem("token"));
    }else{
      comments = await postRequest("/comment/"+theme+"/user", {videogame: filter}, window.localStorage.getItem("token"));
    }
    setComments(comments)
    if(comments.length == 0){
      loadModal("modalNotFound")
    }
  }

  function showComments(comments) {
    if (comments != undefined) {
      return comments.map((comment, key) => {
        return (
          <article>
            <div>
              {showRating(comment.severity)}
            </div>
            <div>
              {comment.text}
            </div>
            <div>
              Category: {comment.category}
            </div>
            <div>
              Upload date: {fixDate(comment.uploadDate)}
            </div>
            <div>
              Video Game: <Link href={{
                pathname: "/selectedVideogame",
                query: {
                  name: comment.videogame
                }
              }}
              >{comment.videogame}</Link>
            </div>
          </article>
        )
      })
    }
  }

  return (
    <Layout>
      <div>
        <form>
          <label for="severity">Filter by severity:</label>
          <select name="severity" id="severity">
            <option value="Mild">Mild</option>
            <option value="Moderate">Moderate</option>
            <option value="Severe">Severe</option>
          </select>
          <input type='button' value="Search" onClick={e => searchBy("severity",document.getElementById("severity").value)} />

          <label for="category">Filter by category:</label>
          <select name="category" id="category">
            <option value="Violence">Violence</option>
            <option value="Drugs">Drugs</option>
            <option value="Language">Language</option>
            <option value="Sexuality">Sexuality</option>
            <option value="Misc">Misc</option>
          </select>
          <input type='button' value="Search" onClick={e => searchBy("category",document.getElementById("category").value)} />

          <input type='search' id='videogame' placeholder='Write video game you want to look your comments off' />
          <input type='button' value="Search" onClick={e => searchBy("videogame",document.getElementById("videogame").value)} />

        </form>
      </div>
      <div>
        {
          showComments(comments)
        }
      </div>
      <dialog id="modalNotFound">
        <article>
          <h2>Not found any comment fullfilling the filter</h2>
          <p>
              Please, search using other filter
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalNotFound")}></input>
          </footer>
        </article>
      </dialog>
    </Layout>
  )
}