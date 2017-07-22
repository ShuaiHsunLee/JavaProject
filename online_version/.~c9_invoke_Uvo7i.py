import os
import subprocess
import json
from flask import Flask, flash, redirect, render_template, request, session, url_for, send_file, send_from_directory
from flask_session import Session
from tempfile import gettempdir
from werkzeug.utils import secure_filename

UPLOAD_FOLDER = './uploads'
ALLOWED_EXTENSIONS = set(['txt'])

# configure application
app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# ensure responses aren't cached
if app.config["DEBUG"]:
    @app.after_request
    def after_request(response):
        response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
        response.headers["Expires"] = 0
        response.headers["Pragma"] = "no-cache"
        return response

# configure session to use filesystem (instead of signed cookies)
app.config["SESSION_FILE_DIR"] = gettempdir()
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

@app.route("/")
def index():
    return render_template("index.html")
        
@app.route("/decipher", methods=["GET", "POST"])
def decipher():
    if request.method == "POST":
        if not request.form.get("sentence"):
            return "must provide sentence"
        
        # save input into text.txt
        text_file = open("./java/text/cipher.txt", "w")
        cipher_result = request.form.get("sentence")
        text_file.write(cipher_result)
        text_file.close()
        
        # decipher
        subprocess.call(['make', 'compile2'])
        subprocess.call(['java', '-jar', 'MyJar2.jar'])
        
        # output deciphered result
        decipher_file = open("./java/text/decipher.txt", "r")
        decipher_result = decipher_file.read().strip('\n')
        decipher_file.close
        return json.dumps({"result":"0000", "cipher" : cipher_result, "decipher" : decipher_result})

@app.route("/cipher", methods=["GET", "POST"])
def cipher():
    if request.method == "POST":
        
        if not request.form.get("sentence"):
            return "must provide sentence"
        
        if not request.form.get("shift"):
            return "must provide shift"
        
        # save input into text.txt
        text_file = open("./java/text/origin.txt", "w")
        string = request.form.get("sentence")
        integer = int(request.form.get("shift"))
        text_file.write(string)
        text_file.close()
        
        # cipher
        subprocess.call(['make', 'compile1'])
        # subprocess.call(['make', 'partialcompile1'])
        subprocess.call(['java', '-jar', 'MyJar1.jar', str(integer)])
        
        # output origin result
        origin_file = open("./java/text/origin.txt", "r")
        origin_result = origin_file.read()
        origin_file.close()
        
        # output ciphered result
        cipher_file = open("./java/text/cipher.txt", "r")
        cipher_result = cipher_file.read().strip('\n')
        cipher_file.close()
        return json.dumps({"result":"0000", "origin" : origin_result, "cipher" : cipher_result, "shift" : integer})

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/upload_file', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'file' not in request.files:
            flash('No file part')
            return redirect(request.url)
        file = request.files['file']
        # if user does not select file, browser also
        # submit a empty part without filename
        if file.filename == '':
            flash('No selected file')
            return redirect(request.url)
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            
            if filename != "Grades_in.txt" or filename :
                return send_from_directory(directory='uploads', filename='error.txt', as_attachment=True)
                
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            subprocess.call(['make', 'compile'])
            subprocess.call(['make', 'run'])
            return send_from_directory(directory='uploads', filename='Grades_out.txt', as_attachment=True)

def apology(top="", bottom=""):
    """Renders message as an apology to user."""
    def escape(s):
        """
        Escape special characters.

        https://github.com/jacebrowning/memegen#special-characters
        """
        for old, new in [("-", "--"), (" ", "-"), ("_", "__"), ("?", "~q"),
            ("%", "~p"), ("#", "~h"), ("/", "~s"), ("\"", "''")]:
            s = s.replace(old, new)
        return s
    return render_template("apology.html", top=escape(top), bottom=escape(bottom))