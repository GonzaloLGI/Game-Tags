import Layout from '../components/layout'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { useEffect, useState } from 'react';
import { postRequest, getRequest } from '../service/backendService';
import { useSearchParams } from 'next/navigation';
import { isCompleteInformation, loadModal, unloadModal } from '../service/miscService';

export default function NewClassification() {

  const [classification, setClassification] = useState([]);

  const videogameName = useSearchParams().get('name')
  const videogameId = useSearchParams().get('id')

  useEffect(() => {
    document.title = "GameTags | Add Classification"
    introJs().setOptions({dontShowAgain: true,tooltipClass: 'customTooltip'}).start();
  }, [])

  async function checkExistingClassificationInVideoGame(URL,classification) {
    if (URL.endsWith("undefined") || URL.endsWith("null")) {
      URL = "/videogame/name/" + videogameName;
    }
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    const classifications = videogame[0].classifications
    return classifications.filter((videogameClassification) => (videogameClassification.system == classification.system && videogameClassification.tag == classification.tag))
  }

  async function addClassification(URL, classification, tagImage) {
    let existingClassification = await checkExistingClassificationInVideoGame("/videogame/name/" + videogameName,classification)
    console.log(existingClassification)
    if(existingClassification.length){
      loadModal("modalExistingClassification")
      return
    }

    if (!isCompleteInformation([classification.system, classification.country, classification.tag, classification.url, tagImage])) {
      loadModal("modalNotInfo")
      return
    }

    if (window.localStorage.getItem("token") == null || window.localStorage.getItem("token") == undefined) {
      window.location = "https://localhost:3000/home"
      return
    }
    const createdClassification = await postRequest(URL, classification, window.localStorage.getItem("token"));
    setClassification(createdClassification)
    await postRequest("/classification/" + createdClassification.classifications.at(-1).id + "/image/" + videogameId, tagImage, window.localStorage.getItem("token"))
    window.localStorage.setItem("name", videogameName)
    window.location = "https://localhost:3000/selectedVideogame?name=" + encodeURIComponent(videogameName)
  }

  return (
    <Layout>
      <div data-title="Creating A New Tag" data-intro="If the video game doesn't have the tag you have in mind, you can add it! You will have to indicate information
        like the entity/system of the tag, the age of the tag or the country where the system is used">
        <form data-title="Age Label Of tag" data-intro="In the age label field you must introduce the short form of the tag age name. If the name combines words with numbers, you should write the
          first letter of the word followed by a '+' and the number. For example, in case you want to add Mature from ESRB, you should write M+17. 
          In case the tag name is only a number or a letter, you should write only the number or letter">
          <div>
            <input type='text' id='entity' placeholder='Write entity name *' />
          </div>
          <div >
            <input type='text' id='age' placeholder='Write age of the label *' />
          </div>
          <div>
            <input type='text' id="url" placeholder='Write URL of the homepage of the entity *' />
          </div>
          <div>
            <input type='text' id='country' placeholder='Write country where the entity belongs *' />
          </div>
          <div>Upload tag image *</div>
          <div>
            <input type="file" id="tagImage" name="image" accept="image/*" />
          </div>
          <input type='button' value="Add new classification" onClick={e => addClassification("/videogame/classification/" + videogameName, {
            system: document.getElementById("entity").value,
            country: document.getElementById("country").value, tag: document.getElementById("age").value, url: document.getElementById("url").value
          }, document.getElementById("tagImage").files[0])} />
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
      <dialog id="modalExistingClassification">
        <article>
          <h2>Video Game already using classification</h2>
          <p>
              The actual video game is already using this classification. Try add a different one
          </p>
          <footer>
            <input type="button" value="Confirm" onClick={e => unloadModal("modalExistingClassification")}></input>
          </footer>
        </article>
      </dialog>
    </Layout>
  )
}