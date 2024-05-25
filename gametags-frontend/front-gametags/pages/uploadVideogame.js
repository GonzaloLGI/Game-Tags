import Layout from '../components/layout'
import Link from 'next/link'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { postRequest, getRequest } from '../service/backendService';
import { isCompleteInformation, loadModal, unloadModal } from '../service/miscService';

export default function UploadVideogame() {

  const [videogames, setVideogame] = useState([]);

  useEffect(() => {
    document.title = "GameTags | Upload New Video Game"
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  function createClassification(entity, age, url, country) {
    return { system: entity, tag: age, url: url, country: country }
  }

  function addPlatforms(platforms) {
    const platformArray = []
    platforms.forEach(element => {
      if (document.getElementById(element).checked) {
        platformArray.push(document.getElementById(element).value)
      }
    });
    return platformArray
  }

  async function videogameAlreadyExists(name) {
    let URL = "/videogame/name/" + name;
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    if (videogame.length > 0) {
      return true
    } else {
      return false
    }
  }

  async function addVideogame(URL, videogame, coverImage, tagImage) {
    if (!isCompleteInformation([videogame.name, videogame.developer, videogame.platforms, videogame.classifications, coverImage, tagImage])) {
      loadModal("modalNotInfo")
      return
    }
    if (window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined) {
      window.location = "https://localhost:3000/home"
      return
    }
    if (await videogameAlreadyExists(videogame.name) == true) {
      loadModal("modalAlreadyExist")
      return
    }
    const createdVideogame = await postRequest(URL, videogame, window.localStorage.getItem("token"));
    setVideogame(createdVideogame)
    await postRequest(URL + createdVideogame.id + "/image", coverImage, window.localStorage.getItem("token"))
    await postRequest("/classification/" + createdVideogame.classifications[0].id + "/image/" + createdVideogame.id, tagImage, window.localStorage.getItem("token"))
    window.localStorage.setItem("name", videogame.name)
    window.location.href = "https://localhost:3000/selectedVideogame?name=" + encodeURIComponent(videogame.name)
  }

  return (
    <Layout>
      <div className='grid'>
        <div data-title="Adding A New Video Game" data-intro="If you want to contribute, you can add a video game. You must specify some information about it, like the name, who 
        has developed it, the platforms it is officially available and a tag, with its corresponding information like the age or the system">
          <form>
            <div>
              <input type='text' id='name' placeholder='Write the video game name *' />
            </div>
            <div>
              <input type='text' id='developer' placeholder='Write the developer name *' />
            </div>
            <div>
              <label for="platforms">Select the platforms where the video game is available *:</label>
              <div className='grid'>
                <div>
                  <div>
                    <input type="checkbox" id="platform1" name="platform1" value="PS5" />
                    <label for="platform1">PS5</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform2" name="platform2" value="Xbox Series X/S" />
                    <label for="platform2">Xbox Series X/S</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform3" name="platform3" value="PS4" />
                    <label for="platform3">PS4</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform4" name="platform4" value="Xbox One" />
                    <label for="platform4">Xbox One</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform5" name="platform5" value="Nintendo Switch" />
                    <label for="platform5">Nintendo Switch</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform6" name="platform6" value="PS3" />
                    <label for="platform6">PS3</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform14" name="platform14" value="PC" />
                    <label for="platform14">PC</label>
                  </div>
                </div>
                <div>
                  <div>
                    <input type="checkbox" id="platform7" name="platform7" value="Xbox 360" />
                    <label for="platform7">Xbox 360</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform8" name="platform8" value="Wii" />
                    <label for="platform8">Wii</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform9" name="platform9" value="PS2" />
                    <label for="platform9">PS2</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform10" name="platform10" value="Xbox" />
                    <label for="platform10">Xbox</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform11" name="platform11" value="Nintendo Gamecube" />
                    <label for="platform11">Nintendo Gamecube</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform12" name="platform12" value="PSP" />
                    <label for="platform12">PSP</label>
                  </div>
                  <div>
                    <input type="checkbox" id="platform13" name="platform13" value="Nintendo DS" />
                    <label for="platform13">Nintendo DS</label>
                  </div>
                </div>
              </div>
            </div>
          </form>
          <form>
            <div>
              <div>Upload game cover image *</div>
              <input type="file" id="image" name="image" accept="image/*" />
            </div>
          </form>
          <form>
            <div>
              <input type="text" id='entity' placeholder='Write the name of the classification entity *' />
            </div>
            <div data-title="Age Label Of tag" data-intro="In this field you must introduce the short form of the tag age name. If the name combines words with numbers, you should write the
          first letter of the word followed by a '+' and the number. For example, in case you want to add Mature from ESRB, you should write M+17. 
          In case the tag name is only a number or a letter, you should write only the number or letter">
              <input type="text" id='age' placeholder='Write the age of the label *' />
            </div>
            <div>
              <input type="text" id='url' placeholder='Write the URL home-page of the classification entity *' />
            </div>
            <div>
              <input type="text" id='country' placeholder='Write the country from where the entity belongs *' />
            </div>
            <div>Upload tag image *</div>
            <div>
              <input type="file" id="tagImage" name="image" accept="image/*" />
            </div>
          </form>
          <div>
            <input type="button" value="Submit new game" onClick={e => addVideogame("/videogame/",
              {
                name: document.getElementById("name").value, developer: document.getElementById("developer").value,
                platforms: addPlatforms(["platform1", "platform2", "platform3", "platform4", "platform5", "platform6", "platform7", "platform8", "platform9", "platform10", "platform11", "platform12","platform13"]),
                classifications: [
                  createClassification(document.getElementById("entity").value, document.getElementById("age").value, document.getElementById("url").value, document.getElementById("country").value)]
                , uploadUser: window.localStorage.getItem("userName")
              }, document.getElementById("image").files[0], document.getElementById("tagImage").files[0])} />
          </div>
        </div>
        <div data-title="System Help" data-intro="If you don't know the age the video game you want to add has, you can search it in the official page of the system. 
        We support multiple systems and they will be shown here">
          <article className='linkCard'>
            <h5>Supported systems</h5>
            <div style={{"margin-bottom": "10px"}}><Link href={{
              pathname: "https://pegi.info/es"
            }}
            >
              <img src="./systems/pegi.jfif" alt='' width="30%" height="50px" />
            </Link></div>
            <div style={{"margin-bottom": "10px"}}><Link href={{
              pathname: "https://www.esrb.org/"
            }}
            ><img src="./systems/ESRB1.png" alt='' width="35%" height="50px" />
            </Link></div>
            <div style={{"margin-bottom": "10px"}}><Link href={{
              pathname: "https://www.classification.gov.au/"
            }}
            ><img src="./systems/acb2.png" alt='' width="75%" height="50px" />
            </Link></div>
            <div style={{"margin-bottom": "10px"}}><Link href={{
              pathname: "https://usk.de/"
            }}
            ><img src="./systems/usk2.png" alt='' width="75%" height="50px" />
            </Link></div>
            <div style={{"margin-bottom": "10px"}}><Link href={{
              pathname: "https://www.bbfc.co.uk/"
            }}
            ><img src="./systems/bbfc1.png" alt='' width="50%" height="50px" />
            </Link></div>
          </article>
        </div>
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
      <dialog id="modalAlreadyExist">
        <article>
          <h2>Video Game already exist in the data base</h2>
          <p>
            Please, upload a different video game
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalAlreadyExist")}></input>
          </footer>
        </article>
      </dialog>
    </Layout>
  )
}