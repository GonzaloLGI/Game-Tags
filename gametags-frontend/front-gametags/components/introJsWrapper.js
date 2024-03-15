// components/IntroWrapper.js
import { useEffect } from 'react';
import introJs from 'intro.js/intro.js';
import 'intro.js/introjs.css';

const IntroWrapper = () => {
  useEffect(() => {
    const intro = introJs();
    intro.setOptions("dontShowAgain",true).start();
  }, []);

  return null;
};

export default IntroWrapper;
{process.browser && <IntroWrapper />}