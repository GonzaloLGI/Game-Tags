import { getRequest } from '../service/backendService';
import Link from 'next/link'
import Layout from '../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import { showRating, fixDate, loadImage } from '../service/miscService'

export default function selectedVideogame() {
  const URL = "/videogame/name/" + useSearchParams().get('name');
  const [videogames, setVideogame] = useState([]);
  const platforms = videogames.platforms;


  useEffect(() => {
    getVideogame(URL);
    checkUserLogged()
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function getVideogame(URL) {
    if (URL.endsWith("undefined") || URL.endsWith("null")) {
      URL = "/videogame/name/" + window.localStorage.getItem("name");
    }
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    if (videogame[0].name != undefined) {
      window.localStorage.setItem("name", videogame[0].name)
      document.title = "GameTags | " + videogame[0].name
    }
    setVideogame(videogame[0])
  }

  function showPlatforms(videogames) {
    if (videogames != undefined && videogames.platforms != undefined) {
      return videogames.platforms.map((element, key) => {
        return (
          <li>{element}</li>
        )
      })
    }
  }

  function showComments(videogames, category) {
    if (videogames != undefined && videogames.comments != undefined) {
      return videogames.comments.map((comment, key) => {
        if (comment.category == category) {
          return (
            <div>
              <article>
                {showRating(comment.severity)}
                <ul>
                  <li>
                    {comment.text}
                  </li>
                  <li>
                    Written by: {addProfileLink(comment.uploadUser)}
                  </li>
                  <li>
                    Upload date: {fixDate(comment.uploadDate)}
                  </li>
                </ul>
              </article>
            </div>
          )
        }
      })
    }
  }

  function addProfileLink(uploadUser) {
    if (uploadUser && window.localStorage.getItem("userName") == uploadUser) {
      return <Link href={{
        pathname: "/profile/userProfile"
      }}
      >{uploadUser}</Link>
    } else {
      return uploadUser
    }
  }

  function loadTagImages(classifications) {
    var image = ""
    if (classifications != undefined) {
      return classifications.map((element, key) => {
        if (element.imageData != undefined || element.imageData != null) {
          image = 'data:image/*;base64,' + element.imageData.data;
        }
        return (
          <div className="tagDisplay">
            <Link href={{
              pathname: element.url
            }}>
              <img decoding='async' id="cover" src={image} width="75px" height="75px"></img>
            </Link>
          </div>
        )
      })
    }
  }

  function calculateRating(category) {
    if (videogames != undefined && videogames.comments != undefined) {
      let mild = videogames.comments.filter(comment => ("Mild" == comment.severity && comment.category == category)).length
      let moderate = videogames.comments.filter(comment => ("Moderate" == comment.severity && comment.category == category)).length
      let severe = videogames.comments.filter(comment => ("Severe" == comment.severity && comment.category == category)).length
      let selectedRating = Math.max(mild, moderate, severe)
      if (selectedRating == 0) {
        return <h6 className='unknown'>Unknown</h6>
      } else if (selectedRating == mild) {
        return <h6 className='mild'>Mild</h6>
      } else if (selectedRating == moderate) {
        return <h6 className='moderate'>Moderate</h6>
      } else if (selectedRating == severe) {
        return <h6 className='severe'>Severe</h6>
      }
    }
  }

  function checkUserLogged() {

    let token = window.localStorage.getItem("token")
    if (token == undefined || token == null) {
      document.getElementById("newClassification").style.display = "none"
      document.getElementById("newComment").style.display = "none"
      document.getElementById("userOptions").style.display = "none"
    } else {
      document.getElementById("newClassification").style.display = "block"
      document.getElementById("newComment").style.display = "block"
      document.getElementById("userOptions").style.display = "block"
    }
  }

  function countComments(theme) {
    if (videogames != undefined && videogames.comments != undefined) {
      let count = videogames.comments.filter(comment => (comment.category == theme)).length
      return count
    }
  }

  return (
    <Layout>
      <article data-title="Video Game Sheet" data-intro="The information of the video game is displayed here, like the platforms where it has been released or when the game was uploaded to the page">
        <div>
          <h1>{videogames.name}</h1>
        </div>
        <div class="grid">
          <div>
            <div>
              <h3>{videogames.developer}</h3>
            </div>
            <div class="grid">
              <div class="coverSmall">
                {loadImage(videogames, "videogame", false)}
              </div>
              <div class="tagsSmall">
                {loadTagImages(videogames.classifications)}
              </div>
            </div>
            <article>
            <div>
              Platforms
              <ul>
                {showPlatforms(videogames)}
              </ul>
            </div>
            <div>
              Upload date: {fixDate(videogames.uploadDateTime)}
            </div>
            <div>
              Upload by: {addProfileLink(videogames.uploadUser)}
            </div>
            </article>
            <article>
              <div>
                Violence rating {calculateRating("Violence")}
              </div>
              <div>
                Language rating {calculateRating("Language")}
              </div>
              <div>
                Sexuality rating {calculateRating("Sexuality")}
              </div>
              <div>
                Drugs rating {calculateRating("Drugs")}
              </div>
              <div>
                Misc rating {calculateRating("Misc")}
              </div>
            </article>
          </div>
          <div>
            <div class="tagsBig">
              {loadTagImages(videogames.classifications)}
            </div>
          </div>
          <div class="coverBig">
            {loadImage(videogames, "videogame", true)}
          </div>
        </div>
      </article>
      <article id='userOptions'>
        <div id='newClassification'>
          <Link href={{
            pathname: "/newClassification",
            query: {
              id: videogames.id,
              name: videogames.name
            }
          }}> Add new classification</Link>
        </div>
        <div id='newComment'>
          <Link href={{
            pathname: "/newComment",
            query: {
              id: videogames.id,
              name: videogames.name
            }
          }}> Add new comment</Link>
        </div>
      </article>
      <article data-title="Comments" data-intro="The comments are separated in each thematic matter. The themes are violence, language, sexaulityt, drugs and miscellaneous">
        <div>
          <details className='dropdown'>
            <summary>Violence Comments: {countComments("Violence")}</summary>
            <div>
              {showComments(videogames, "Violence")}
            </div>
          </details>
        </div>
      </article>
      <article data-title="Comment's Information" data-intro="Each comment indicates the severity judge by the user, the comment itself about their opinion and who uploaded the comment">
        <div>
          <details className='dropdown'>
            <summary>Language Comments: {countComments("Language")}</summary>
            <div>
              {showComments(videogames, "Language")}
            </div>
          </details>
        </div>
      </article>
      <article>
        <div>
          <details className='dropdown'>
            <summary>Sexuality Comments: {countComments("Sexuality")}</summary>
            <div>
              {showComments(videogames, "Sexuality")}
            </div>
          </details>
        </div>
      </article>
      <article>
        <div>
          <details className='dropdown'>
            <summary>Drug Comments: {countComments("Drugs")}</summary>
            <div>
              {showComments(videogames, "Drugs")}
            </div>
          </details>
        </div>
      </article>
      <article>
        <div>
          <details className='dropdown'>
            <summary>Misc Comments: {countComments("Misc")}</summary>
            <div>
              {showComments(videogames, "Misc")}
            </div>
          </details>
        </div>
      </article>

    </Layout>
  )
}