import Layout from '../../components/layout'
import Link from 'next/link'
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';
import { getRequest } from '../../service/backendService';
import { useEffect, useCallback, useState } from 'react';

export default function Home() {

  const [videogames, setVideogame] = useState([]);

  useEffect(() => {
    document.title = "GameTags | Home Page"
    threeLatestAddedVideogames("/videogame/latest");
    introJs().setOption("dontShowAgain", true).start();
  }, [])

  async function threeLatestAddedVideogames(URL) {
    const videogame = await getRequest(URL, window.localStorage.getItem("token"));
    if (videogame.constructor == Array) {
      setVideogame(videogame)
    } else {
      setVideogame([videogame])
    }
  }

  return (
    <Layout>
      <div>
        <div data-title="Latest Incorporations" data-intro="Here you will see the 3 latest games added to the web">Latest incorporations</div>
        <div class="grid">
          {
            videogames.map((videogame, key) => {
              const name = videogame.name;
              let image = ""
              if (videogame.imageData != undefined || videogame.imageData != null) {
                image = 'data:image/*;base64,' + videogame.imageData.data;
              }
              return (
                <article>
                  <div className="incorporation">
                    <Link href={{
                      pathname: "/selectedVideogame",
                      query: {
                        name: name
                      }
                    }}
                    >
                      <img decoding='async' id="image" src={image} width="300px"></img>
                    </Link>
                  </div>
                </article>
              )
            })
          }
        </div>
      </div>
    </Layout>
  )
}


