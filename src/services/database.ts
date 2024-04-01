import sql from "mssql";

const dbSettings = {
    user: "rolando1010_SQLLogin_1",
    password: "enc1x9m8mq",
    server: "zephyrdb.mssql.somee.com",
    database: "zephyrdb",
    options: {trustServerCertificate: true}
};

let connection:any;
(async () => {
    connection = await sql.connect(dbSettings);
})();

const databaseQuery = (query:string, rawResult:boolean=false): Promise<any> => {
    console.log(query);
    return new Promise(resolve => {
        connection.request().query(query).then((result: any) => resolve(rawResult ? result : result.recordset));
    });
}

export default databaseQuery;