FROM  21-alpine3.18
WORKDIR /app
COPY . .
RUN npm install
WORKDIR /app
RUN npm run build
WORKDIR /app
EXPOSE 3001
CMD ["npm", "start"]
