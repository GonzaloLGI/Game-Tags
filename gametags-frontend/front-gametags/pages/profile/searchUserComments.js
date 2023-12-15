import Layout from '../../components/layout'
import { getRequest, postRequest } from '../../service/backendService';
import { useEffect, useCallback, useState } from 'react';
import Link from 'next/link'

export default function SearchUserComments() {

  const [comments, setComments] = useState([]);


  async function searchBySeverity(severity) {
    console.log("Severity: " + severity)
    const comments = await postRequest("/comment/severity/user", severity,window.localStorage.getItem("token"));
    console.log("Comments: " + comments)
    setComments(comments)
  }

  async function searchByCategory(category) {
    console.log("Category: " + category)
    const comments = await postRequest("/comment/category/user", category,window.localStorage.getItem("token"));
    console.log("Comments: " +  comments)
    setComments(comments)
  }

  async function searchByVideogame(videogame) {
    console.log("Videogame: " + videogame)
    const comments = await postRequest("/comment/videogame/user", {videogame:videogame},window.localStorage.getItem("token"));
    console.log("Comments: " + comments)
    setComments(comments)
  }

  function showComments(comments){
    if (comments != undefined) {
      return comments.map((comment, key) => {
          return (
            <ul>
              <li>
                Severidad: {comment.severity}
              </li>
              <li>
                Texto: {comment.text}
              </li>
              <li>
                Categoria: {comment.category}
              </li>
              <li>
                Fecha de subida: {comment.uploadDate}
              </li>
              <li>
                Videojuego: <Link href={{
                        pathname:"/selectedVideogame",
                        query: {
                          name: comment.videogame
                        }
                      }}
                      >{comment.videogame}</Link>
              </li>
            </ul>
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
          <input type='button' value="Search" onClick={e => searchBySeverity(document.getElementById("severity").value)} />

          <label for="category">Filter by category:</label>
          <select name="category" id="category">
            <option value="Violence">Violence</option>
            <option value="Drugs">Drugs</option>
            <option value="Language">Language</option>
            <option value="Sexuality">Sexuality</option>
            <option value="Misc">Misc</option>
          </select>
          <input type='button' value="Search" onClick={e => searchByCategory(document.getElementById("category").value)} />

          <input type='search' id='videogame' placeholder='Write videogame you want to look your comments off' />
          <input type='button' value="Search" onClick={e => searchByVideogame(document.getElementById("videogame").value)} />

        </form>
      </div>
      <div>
        {
          showComments(comments)
        }
      </div>
    </Layout>
  )
}