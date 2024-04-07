import { getRequest } from './backendService';


export function showRating(severity) {
  if (!severity) {
    return <h6 className='unknown'>Unknown</h6>
  } else if (severity == "Mild") {
    return <h6 className='mild'>Mild</h6>
  } else if (severity == "Moderate") {
    return <h6 className='moderate'>Moderate</h6>
  } else if (severity == "Severe") {
    return <h6 className='severe'>Severe</h6>
  }
}

export function loadModal(name) {
  document.getElementById(name).setAttribute("open", true)
}

export function unloadModal(name) {
  document.getElementById(name).setAttribute("open", false)
}

export function isCompleteInformation(info) {
  let complete = true;
  info.forEach(element => {
    if (!element || element == null || element.length <= 0) {
      complete = false
    }
  });
  return complete;
}

export function fixDate(dateTime) {
  if (dateTime) {
    return dateTime[2] + "/" + dateTime[1] + "/" + dateTime[0]
  }
}

export async function getUserRequest(username) {
  let URL = "/user/name/" + username
  const user = await getRequest(URL, window.localStorage.getItem("token"));
  return user
}

export function loadImage(element, page, big) {
  var image = ""
  switch (page) {
    case "profile":
      if (element.profileImageData) {
        image = 'data:image/*;base64,' + element.profileImageData.data;
        return (
          <img className="profileImageStyle" decoding='async' id="cover" src={image}></img>
        )
      } else {
        return (
          <img src="../images/unknown-user.png"></img>
        )
      }
      break;
    case "videogame":
      if (element.imageData) {
        if (big == true){
          image = 'data:image/*;base64,' + element.imageData.data;
          return (
            <img className="coverBigImage" decoding='async' id="cover" src={image}></img>
          )
        } else{
          image = 'data:image/*;base64,' + element.imageData.data;
          return (
            <img className='coverSmallImage' decoding='async' id="cover" src={image}></img>
          )
        }
      }
      break;
    case "navbar":
      if (element.profileImageData) {
        image = 'data:image/*;base64,' + element.profileImageData.data;
        return (
          <img decoding='async' id="cover" src={image}></img>
        )
      } else {
        return (
          <img src="../images/unknown-user.png"></img>
        )
      }
  }

}