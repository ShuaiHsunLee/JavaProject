import os
import subprocess
from flask import Flask, flash, redirect, render_template, request, session, url_for, send_file
from flask_session import Session
from tempfile import gettempdir
from werkzeug.utils import secure_filename

UPLOAD_FOLDER = '/home/ubuntu/workspace/uploads'
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
    return render_template("project.html")
    
@app.route("/project1")
def project1():
    return render_template("cipher.html")

@app.route("/project2")
def project2():
    return render_template("decipher.html")
    
@app.route("/decipher", methods=["GET", "POST"])
def decipher():
    if request.method == "POST":
        if not request.form.get("sentence"):
            return apology("must provide sentence")
        
        # save input into text.txt
        text_file = open("./java/text/cipher.txt", "w")
        cipher_result = request.form.get("sentence")
        text_file.write(cipher_result)
        text_file.close()
        
        # decipher
        # subprocess.call(['make', 'compile2'])
        subprocess.call(['java', '-jar', 'MyJar2.jar'])
        
        # output deciphered result
        decipher_file = open("./java/text/decipher.txt", "r")
        decipher_result = decipher_file.read()
        decipher_file.close
        return render_template("decipher_output.html", cipher = cipher_result, decipher = decipher_result)

@app.route("/cipher", methods=["GET", "POST"])
def cipher():
    if request.method == "POST":
        
        if not request.form.get("sentence"):
            return apology("must provide sentence")
        
        if not request.form.get("shift"):
            return apology("must provide shift")
        
        # save input into text.txt
        text_file = open("./java/text/origin.txt", "w")
        string = request.form.get("sentence")
        integer = int(request.form.get("shift"))
        text_file.write(string)
        text_file.close()
        
        # cipher
        # subprocess.call(['make', 'compile1'])
        subprocess.call(['make', 'partialcompile1'])
        subprocess.call(['java', '-jar', 'MyJar1.jar', str(integer)])
        
        # output origin result
        origin_file = open("./java/text/origin.txt", "r")
        origin_result = origin_file.read()
        origin_file.close()
        
        # output ciphered result
        cipher_file = open("./java/text/cipher.txt", "r")
        cipher_result = cipher_file.read()
        cipher_file.close()
        return render_template("cipher_output.html", origin = origin_result, cipher = cipher_result, shift = integer)

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
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            # return render_template("uploaded.html", filename = filename)
            return redirect(url_for("return_files_tut"))
    return render_template("upload_file.html")

@app.route('/return-files/')
def return_files_tut():
    subprocess.call(['make', 'compile'])
    subprocess.call(['make', 'run'])
    return send_file('/home/ubuntu/workspace/uploads/Grades_out.txt', attachment_filename='Grades_out.txt')

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