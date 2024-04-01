import axios from "axios";

const sendMail = async (destino:string, asunto:string, mensaje:string) => {
    return new Promise((resolve, reject) => {
        const data = JSON.stringify({
            "Messages": [{
                "From": {"Email": "tarea4progra4@gmail.com", "Name": "Zephyr"},
                "To": [{"Email": destino, "Name": "Zehpyr User"}],
                "Subject": asunto,
                "TextPart": mensaje
            }]
        });
        
        const config = {
            method: 'post',
            url: 'https://api.mailjet.com/v3.1/send',
            data: data,
            headers: { 'Content-Type': 'application/json' },
            auth: { username: 'f6cee44dc39753921919d6e00a3ee511', password: 'e3f8822e218d1bc49d86713443756405' },
        };
        
        axios(config)
            .then(resolve)
            .catch(reject);
    });
}

export default sendMail;