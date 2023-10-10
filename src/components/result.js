import React from 'react';

const Result = ({isWin}) => {
  return (
      <p className="result">
        {isWin ? 'You win!' : 'Sorry, you lost :('}
      </p>
  );
};

export default Result;
