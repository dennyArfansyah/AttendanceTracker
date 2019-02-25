const jwt = require('jsonwebtoken');
const fs   = require('fs');
var publicKey = fs.readFileSync('key/public.key', 'utf8');

module.exports = (req, res, next) => {
    try{
        var verifyOptions = {
            issuer: "Blockchain Authentication",
            audience: "http://thisapi.auth.com/",
            algorithm:  ["RS256"],
            scope: "read:data, write-date",
            expiresIn : '1h' 
        };
        const token = req.headers.authorization.split(" ")[1];
        const decode = jwt.verify(token, publicKey, verifyOptions);
        req.userData = decode;
        next();
    }catch(error){
        return res.status(401).json({
            status : "error",
            message : "Authentication Failed"
        });
    }
};
