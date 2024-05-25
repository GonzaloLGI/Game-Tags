import Layout from '../../components/layout'
import { postRequest } from '../../service/backendService';
import { loadModal, unloadModal, showRating, fixDate } from '../../service/miscService';
import { useEffect, useState } from 'react';
import Link from 'next/link'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';

export default function SearchUserComments() {
  const username = window.localStorage.getItem('userName');
  const [comments, setComments] = useState([]);

  useEffect(() => {
    document.title = "GameTags | Search User Comments"
    introJs().setOptions({ dontShowAgain: true, tooltipClass: 'customTooltip' }).start();
  }, [])

  async function searchBy(theme, filter) {
    let comments;
    if (theme != "videogame") {
      comments = await postRequest("/comment/" + theme + "/user", filter, window.localStorage.getItem("token"));
    } else {
      if (filter) {
        comments = await postRequest("/comment/" + theme + "/user", { videogame: filter }, window.localStorage.getItem("token"));
      } else{
        loadModal("modalNotFound")
        return
      }
    }
    setComments(comments)
    if (comments.length == 0) {
      loadModal("modalNotFound")
    }
  }

  function showComments(comments) {
    if (comments != undefined || comments != null) {
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
      <div data-title='Search Your Comments' data-intro="In case you want to track all your comments and opinions, here is your place. 
      You can not only look for all your comments, you can filter them.">
        <form data-title='The Filters' data-intro="You can filter by different things, such as the severity you gave,
        a specific category like violence or drugs or, if you have written several for a specific game, you can search them by the video game's name">
          <label for="severity">Filter by severity:</label>
          <select name="severity" id="severity">
            <option value="Mild">Mild</option>
            <option value="Moderate">Moderate</option>
            <option value="Severe">Severe</option>
          </select>
          <input type='button' value="Search" onClick={e => searchBy("severity", document.getElementById("severity").value)} />

          <label for="category">Filter by category:</label>
          <select name="category" id="category">
            <option value="Violence">Violence</option>
            <option value="Drugs">Drugs</option>
            <option value="Language">Language</option>
            <option value="Sexuality">Sexuality</option>
            <option value="Misc">Misc</option>
          </select>
          <input type='button' value="Search" onClick={e => searchBy("category", document.getElementById("category").value)} />

          <input type='search' id='videogame' placeholder='Write video game you want to look your comments off' />
          <input type='button' value="Search" onClick={e => searchBy("videogame", document.getElementById("videogame").value)} />

        </form>
      </div>
      <div data-title='The Results' data-intro="Once you have selected a filter, the comments fullfilling that filter will be shown here.">
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