import Link from 'next/link'
import Layout from '../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { getRequest, postRequest } from '../service/backendService';
import { loadModal, unloadModal } from '../service/miscService';

export default function Search() {

  const [videogames, setVideogame] = useState([]);

  useEffect(() => {
    document.title= "GameTags | Search Video Game"
    introJs().setOption("dontShowAgain", true).start();
  }, [])

  async function searchVideogame(URL,criteria) {
    if(!criteria){
      loadModal("modalNotFound")
      return
    }
    URL = URL + criteria
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    if (videogame.constructor == Array) {
      if(videogame.length == 0 || !videogame[0].id){
        console.log("jqnwujiedh")
        loadModal("modalNotFound")
        return
      }
      setVideogame(videogame)
    } else {
      if(!videogame.id){
        console.log("jqnwujiedh")
        loadModal("modalNotFound")
        return
      }
      setVideogame([videogame])
    }
  }

  async function searchVideogameByPlatforms(URL, platforms) {
    const videogame = await postRequest(URL, platforms, window.localStorage.getItem("token"));
    if (videogame.constructor == Array) {
      setVideogame(videogame)
    } else {
      setVideogame([videogame])
    }
    if(videogame.length == 0){
      loadModal("modalNotFound")
    }
  }

  return (
    <Layout>
      <form data-title="Search Filters" data-intro="You have multiple options to search a specific game or a list of them">
        <div data-title="Filter By Name" data-intro="You can look for a specific video game by its name or search a group of them that contains the same word">
          <input type="search" id='name' placeholder='Write video game name' />
          <input type="button" value="Search by name" onClick={e => searchVideogame("/videogame/name/",document.getElementById("name").value)} />
        </div>
        <div data-title="Filter By Age" data-intro="You can also search by the age tag that the game has">
          <label for="age">Select an age to filter:</label>
          <select name="age" id="age">
            <option value="3">3</option>
            <option value="7">7</option>
            <option value="12">12</option>
            <option value="16">16</option>
            <option value="18">18</option>
          </select>
          <input type="button" value="Search by age" onClick={e => searchVideogame("/videogame/tag/",document.getElementById("age").value)} />
        </div>
        <div data-title="Filter By Platform" data-intro="Or you can search the games by the platforms they have been officially released">
          <label for="platform">Select a platform to filter:</label>
          <select name="platform" id="platform">
            <option value="Xbox 360">Xbox 360</option>
            <option value="PS3">PS3</option>
            <option value="Wii">Wii</option>
            <option value="Xbox One">Xbox One</option>
            <option value="PS4">PS4</option>
            <option value="Nintendo Switch">Nintendo Switch</option>
            <option value="PS2">PS2</option>
            <option value="Xbox">Xbox</option>
            <option value="Nintendo Gamecube">Nintendo Gamecube</option>
            <option value="PSP">PSP</option>
            <option value="Nintendo DS">Nintendo DS</option>
            <option value="PC">PC</option>
          </select>
          <input type="button" value="Search by platform" onClick={e => searchVideogameByPlatforms("/videogame/platforms", [document.getElementById("platform").value])} />
        </div>
        <div data-title="Filter By System" data-intro="You can also search games by a specific rating system">
          <label for="system">Select an system to filter:</label>
          <select name="system" id="system">
            <option value="PEGI">PEGI</option>
            <option value="ESRB">ESRB</option>
            <option value="BBFC">BBFC</option>
            <option value="USK">USK</option>
          </select>
          <input type="button" value="Search by system" onClick={e => searchVideogame("/videogame/system/",document.getElementById("system").value)} />
        </div>
        <div data-title="Search By Developer" data-intro="Or search the games of a specific developer">
          <input type="search" id='developer' placeholder='Write developer name' />
          <input type="button" value="Search by developer" onClick={e => searchVideogame("/videogame/developer/",document.getElementById("developer").value)} />
        </div>
      </form>
      <ul data-title="Found Video Games" data-intro="The result of your search will be displayed here">
        <li>
          Found video games:
          <div>
            {
              videogames.map((videogame, key) => {
                const name = videogame.name;
                let image = ""
                if(videogame.imageData != undefined || videogame.imageData != null){
                   image = 'data:image/*;base64,' + videogame.imageData.data;
                }
                return (
                  <article>
                    <div class="grid">
                      <div>
                        <Link href={{
                          pathname: "/selectedVideogame",
                          query: {
                            name: name
                          }
                        }}
                        >{videogame.name}</Link>
                      </div>
                      <div class="imageDisplay">
                          <img decoding='async' id="image" src={image} width="300px" text-align="right"></img>
                      </div>
                    </div>
                  </article>
                )
              })
            }
          </div>
        </li>
      </ul>
      <dialog id="modalNotFound">
        <article>
          <h2>Not found any game fullfilling the filter</h2>
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