import React from 'react';

const Result = ({isWin, handleRestart}) => {
  return (
      <div className="main__result">
        <p className={`main__result-text ${isWin
                ? 'main__result-text_win'
                : 'main__result-text_lose'} animate__animated` +
            ` animate__lightSpeedInLeft`}>
          {isWin ? 'You win!' : 'Sorry, you lost :('}
        </p>
        <button className="btn btn-outline-primary"
                onClick={handleRestart}>Try again
        </button>
      </div>
  );
};

export default Result;
